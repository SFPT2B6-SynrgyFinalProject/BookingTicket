package id.synrgy6team2.bookingticket.presentation.bookingdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.createLoadingDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.toImgUrl
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.presentation.MainActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityBookingDetailBinding
import javax.inject.Inject

@AndroidEntryPoint
class BookingDetailActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookingPassengerAdapterAdapter
    private lateinit var binding: ActivityBookingDetailBinding
    private val viewModel: BookingDetailViewModel by viewModels()
    private val dialog: AlertDialog by lazy { createLoadingDialog() }
    private val orderId: String by lazy { intent.getStringExtra(EXTRA_PASSING_ORDER_ID) ?: "" }
    private val modeFlags: Boolean by lazy { intent.getBooleanExtra(EXTRA_BACKPRESSED_TRUE, false) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.orderDetail.observe(this) { state ->
            handleStateOrderDetail(state, {
                dialog.show()
                binding.contentNotFound.isVisible = false
                binding.content.isVisible = false
            }, { data ->
                dialog.dismiss()
                binding.contentNotFound.isVisible = false
                binding.content.isVisible = true
                showDataOrderDetail(data)
            }, { message ->
                dialog.dismiss()
                binding.contentNotFound.isVisible = true
                binding.content.isVisible = false
                onToast("Error!", message, StyleType.ERROR)
            })
        }
        viewModel.orderDetail(orderId)
    }

    @SuppressLint("SetTextI18n")
    private fun showDataOrderDetail(data: GetOrderDetailResponseModel?) {
        if (data?.data?.paymentStatus.equals("paid")) binding.txtPaymentStatus.setTextColor(
            ContextCompat.getColor(this, R.color.secondary_success)
        )

        binding.txtPaymentStatus.text = "${data?.data?.paymentStatus?.uppercase()}"
        binding.tvAircraftType.text = "${data?.data?.flightDetails?.airline?.name} ${data?.data?.flightDetails?.flightCode}"
        binding.tvFlightClass.text = "${data?.data?.flightClass}"
        binding.layoutFlightAway.tvDepartureCity.text = "${data?.data?.flightDetails?.departure?.city} (${data?.data?.flightDetails?.departure?.code})"
        binding.layoutFlightAway.tvDestination.text = "${data?.data?.flightDetails?.arrival?.city} (${data?.data?.flightDetails?.arrival?.code})"
        binding.layoutFlightAway.tvDepartureTime.text = "${data?.data?.flightDetails?.departure?.dateTime?.parseToTime()}"
        binding.layoutFlightAway.tvDepartureDate.text = "${data?.data?.flightDetails?.departure?.dateTime?.toCustomFormat()}"
        binding.layoutFlightAway.tvArrivalTime.text = "${data?.data?.flightDetails?.arrival?.dateTime?.parseToTime()}"
        binding.layoutFlightAway.tvArrivalDate.text = "${data?.data?.flightDetails?.arrival?.dateTime?.toCustomFormat()}"
        binding.layoutFlightAway.tvDepartureAirport.text = "${data?.data?.flightDetails?.departure?.airportName}"
        binding.layoutFlightAway.tvDestinationAirport.text = "${data?.data?.flightDetails?.arrival?.airportName}"
        binding.imgAirline.load(data?.data?.flightDetails?.airline?.iconUrl.toImgUrl()) {
            crossfade(true)
            placeholder(R.drawable.img_loading)
            error(R.drawable.img_not_found)
            size(ViewSizeResolver(binding.imgAirline))
        }

        adapter.submitList(viewModel.passengerDetail(
            data?.data?.orderer?.fullName,
            data?.data?.passengerDetails
        ))
    }

    private fun bindView() {
        onBackPressedDispatcher.addCallback {
            if (modeFlags) {
                val intent = Intent(this@BookingDetailActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            finish()
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.rvPassenger.setHasFixedSize(false)
        binding.rvPassenger.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvPassenger.itemAnimator = DefaultItemAnimator()
        binding.rvPassenger.isNestedScrollingEnabled = false
        binding.rvPassenger.adapter = adapter
    }

    private fun handleStateOrderDetail(
        state: State<GetOrderDetailResponseModel>,
        onLoading: () -> Unit,
        onSuccess: (GetOrderDetailResponseModel?) -> Unit,
        onError: (String?) -> Unit) {
        when (state) {
            is State.Loading -> { onLoading.invoke() }
            is State.Success -> { onSuccess.invoke(state.data) }
            is State.Error -> { onError.invoke(state.message) }
        }
    }

    companion object {
        private const val EXTRA_PASSING_ORDER_ID: String = "EXTRA_PASSING_ORDER_ID"
        private const val EXTRA_BACKPRESSED_TRUE: String = "EXTRA_BACKPRESSED_TRUE"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            orderId: String?
        ): Intent {
            return Intent(context, BookingDetailActivity::class.java).apply {
                putExtra(EXTRA_PASSING_ORDER_ID, orderId)
            }
        }

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            orderId: String?,
            modeFlags: Boolean?
        ): Intent {
            return Intent(context, BookingDetailActivity::class.java).apply {
                putExtra(EXTRA_PASSING_ORDER_ID, orderId)
                putExtra(EXTRA_BACKPRESSED_TRUE, modeFlags)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}