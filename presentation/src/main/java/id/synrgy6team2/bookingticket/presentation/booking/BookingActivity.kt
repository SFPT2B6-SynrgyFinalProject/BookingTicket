package id.synrgy6team2.bookingticket.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.common.parcelable
import id.synrgy6team2.bookingticket.common.parseToTime
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityBookingBinding
import javax.inject.Inject

@AndroidEntryPoint
class BookingActivity : AppCompatActivity() {

    @Inject lateinit var adapter: BookingPassengerAdapter
    private lateinit var binding: ActivityBookingBinding
    private val viewModel: BookingViewModel by viewModels()
    private val ticket: TicketRequestModel by lazy {
        intent.parcelable(EXTRA_PASSING_TICKET) ?: TicketRequestModel() }
    private val flightItem: TicketResponseModel.Data.AvailableFlightItem by lazy {
        intent.parcelable(EXTRA_PASSING_FLIGHT_ITEM) ?: TicketResponseModel.Data.AvailableFlightItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.itemForms.observe(this) {
            adapter.submitList(it)
        }

        viewModel.showProfile.observe(this) { profile ->
            binding.txtFullName.setText(profile.data?.fullName ?: "")
            binding.txtEmail.setText(profile.data?.email ?: "")
            binding.txtNoHp.setText(profile.data?.noHp ?: "")
        }

        viewModel.createOrder.observe(this) { order ->
            when (order) {
                is State.Loading -> {
                    onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success -> {
                    createMessageDialog(
                        getString(R.string.txt_verify_successfully),
                        getString(R.string.txt_successfully_create_order)
                    ) {
                        it.dismiss()

                    }
                }
                is State.Error -> {
                    onToast(
                        "Error!",
                        order.message,
                        StyleType.ERROR
                    )
                }
            }
        }
    }

    private fun bindView() {
        if (ticket.passenger?.adult == 1) {
            binding.tvPassengerDetails.visibility = View.GONE
        }

        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            onContinue()
        }

        bindTicket()
        bindAdapter()

        viewModel.itemForms(ticket.passenger?.adult ?: 0)
    }

    private fun bindTicket() {
        binding.layoutItemDepartureSchedule.tvDepartureCity.text = flightItem.departureAirportCode
        binding.layoutItemDepartureSchedule.tvDestination.text = flightItem.arrivalAirportCode
        binding.layoutItemDepartureSchedule.tvDepartureTime.text = flightItem.departureDateTime?.parseToTime() ?: "00:00"
        binding.layoutItemDepartureSchedule.tvArrivalTime.text = flightItem.arrivalDateTime?.parseToTime() ?: "00:00"
        binding.layoutItemDepartureSchedule.tvArrivalDate.text = flightItem.departureDateTime?.toCustomFormat()
        binding.layoutItemDepartureSchedule.tvPassengerCount.text = "${ticket.passenger?.adult} orang"
        binding.layoutItemDepartureSchedule.tvFlightClass.text = ticket.classFlight
    }

    private fun onContinue() {
        onValidation(
            validationParams = arrayOf(
                binding.tilFullNameOrder to ValidationType.GENERAL,
                binding.tilPhoneNumber to ValidationType.GENERAL,
                binding.tilEmailAddress to ValidationType.EMAIL,
                binding.passenger to ValidationType.GENERAL
            ),
            onConfirmPassword = null
        ) {
            val value = CreateOrderRequestModel(
                classId = ticket.classId,
                ticketId = flightItem.ticketId,
                orderer = CreateOrderRequestModel.Orderer(
                    binding.txtNoHp.text.toString(),
                    "${binding.txtPassenger.text} ${binding.txtFullName.text}",
                    binding.txtEmail.text.toString()
                ),
                passengerDetails = CreateOrderRequestModel.PassengerDetails(
                    viewModel.saveForms.value
                )
            )
            viewModel.createOrder(value)
        }
    }

    private fun bindAdapter() {
        binding.btnContinue.isEnabled = false
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter

        adapter.onChange(object : BookingPassengerAdapter.BookingPassengerListener {
            override fun onChange(position: Int, value: String) {
                val item = viewModel.saveForms.value?.filter { it.isEmpty() }?.size ?: 0
                if (item == 0) {
                    binding.btnContinue.isEnabled = true
                }
                viewModel.saveForms(position, value)
            }
        })
    }

    companion object {
        private const val EXTRA_PASSING_TICKET: String = "EXTRA_PASSING_TICKET"
        private const val EXTRA_PASSING_FLIGHT_ITEM: String = "EXTRA_PASSING_FLIGHT_ITEM"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            ticket: TicketRequestModel?,
            flightItem: TicketResponseModel.Data.AvailableFlightItem?
        ): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                ticket?.let { putExtra(EXTRA_PASSING_TICKET, it) }
                flightItem?.let { putExtra(EXTRA_PASSING_FLIGHT_ITEM, it) }
            }
        }
    }
}