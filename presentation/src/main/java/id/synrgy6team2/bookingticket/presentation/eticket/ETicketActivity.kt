package id.synrgy6team2.bookingticket.presentation.eticket

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.parcelable
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.toImgUrl
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityETicketBinding
import javax.inject.Inject

@AndroidEntryPoint
class ETicketActivity : AppCompatActivity() {

    @Inject lateinit var adapter: ETicketAdapter
    private lateinit var binding: ActivityETicketBinding

    private val viewModel: ETicketViewModel by viewModels()
    private val item: GetOrderDetailResponseModel by lazy {
        intent.parcelable(PASSING_DATA_FROM_BOOKING_DETAIL) ?: GetOrderDetailResponseModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityETicketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        adapter.submitList(viewModel.passengerDetail(
            item.data?.orderer?.fullName,
            item.data?.passengerDetails
        ))

        binding.rvPassenger.setHasFixedSize(false)
        binding.rvPassenger.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvPassenger.itemAnimator = DefaultItemAnimator()
        binding.rvPassenger.isNestedScrollingEnabled = false
        binding.rvPassenger.adapter = adapter

        binding.txtTanggalPembayaran.text = "${item.data?.paymentTime?.parseToTime()} ${item.data?.flightDetails?.departure?.dateTime?.toCustomFormat()}"
        binding.tvBookingID.text = "Booking ID: ${item.data?.orderId}"
        binding.tvAircraftType.text = "${item.data?.flightDetails?.airline?.name} ${item.data?.flightDetails?.flightCode}"
        binding.tvFlightClass.text = "${item.data?.flightClass}"
        binding.tvDepartureCity.text = "${item.data?.flightDetails?.departure?.city} (${item.data?.flightDetails?.departure?.code})"
        binding.tvDestination.text = "${item.data?.flightDetails?.arrival?.city} (${item.data?.flightDetails?.arrival?.code})"
        binding.tvDepartureTime.text = "${item.data?.flightDetails?.departure?.dateTime?.parseToTime()}"
        binding.tvDepartureDate.text = "${item.data?.flightDetails?.departure?.dateTime?.toCustomFormat()}"
        binding.tvArrivalTime.text = "${item.data?.flightDetails?.arrival?.dateTime?.parseToTime()}"
        binding.tvArrivalDate.text = "${item.data?.flightDetails?.arrival?.dateTime?.toCustomFormat()}"
        binding.tvDepartureAirport.text = "${item.data?.flightDetails?.departure?.airportName}"
        binding.tvDestinationAirport.text = "${item.data?.flightDetails?.arrival?.airportName}"
        binding.imgAirline.load(item.data?.flightDetails?.airline?.iconUrl.toImgUrl()) {
            crossfade(true)
            placeholder(R.drawable.img_loading)
            error(R.drawable.img_not_found)
            size(ViewSizeResolver(binding.imgAirline))
        }
    }

    companion object {
        private const val PASSING_DATA_FROM_BOOKING_DETAIL: String = "PASSING_DATA_FROM_BOOKING_DETAIL"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            item: GetOrderDetailResponseModel?
        ): Intent {
            return Intent(context, ETicketActivity::class.java).apply {
                putExtra(PASSING_DATA_FROM_BOOKING_DETAIL, item)
            }
        }
    }
}