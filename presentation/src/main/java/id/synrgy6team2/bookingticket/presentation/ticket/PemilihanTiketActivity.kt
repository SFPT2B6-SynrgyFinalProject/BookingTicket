package id.synrgy6team2.bookingticket.presentation.ticket

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.parcelable
import id.synrgy6team2.bookingticket.common.toCustomFormat
import id.synrgy6team2.bookingticket.common.truncateString
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel
import id.synrgy6team2.bookingticket.presentation.booking.BookingActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPemilihanTiketBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class PemilihanTiketActivity : AppCompatActivity() {

    @Inject lateinit var adapter: PemilihanTiketAdapter
    private lateinit var binding: ActivityPemilihanTiketBinding
    private val viewModel: PemilihanTiketViewModel by viewModels()
    private val ticketRequest: TicketRequestModel by lazy {
        intent.parcelable(EXTRA_PASSING_PEMILIHAN_TIKET) ?: TicketRequestModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemilihanTiketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        val item = ticketRequest
        viewModel.getTicket(item)

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onClick { _, output ->
            val intent = BookingActivity.getIntentTo(
                this,
                ticketRequest,
                output
            )
            startActivity(intent)
        }

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.txtKotaCode.text = "${item.departureCity?.truncateString(1)}(${item.departureCode})"
        binding.txtKodeCodeTiba.text = "${item.arrivalCity?.truncateString(1)}(${item.arrivalCode})"
        binding.txtDepartureDate.text = item.departureDateStart?.toCustomFormat()
        binding.txtType.text = item.classFlight
        binding.txtHuman.text = "${item.passenger?.adult.toString()} orang"
        binding.btnCalendar.setOnClickListener { datePicker() }
        binding.nsvContent.setHasFixedSize(false)
        binding.nsvContent.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.nsvContent.itemAnimator = DefaultItemAnimator()
        binding.nsvContent.isNestedScrollingEnabled = false
        binding.nsvContent.adapter = adapter
    }

    private fun bindObserver() {
        viewModel.getTicket.observe(this) { state: State<TicketResponseModel> ->
            when (state) {
                is State.Loading -> {
                    binding.contentNotFound.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.nsvContent.visibility = View.GONE
                }

                is State.Success -> {
                    if (state.data?.data?.availableFlight?.isEmpty() == true) {
                        binding.progressBar.visibility = View.GONE
                        binding.contentNotFound.visibility = View.VISIBLE
                        binding.nsvContent.visibility = View.GONE
                        adapter.submitList(emptyList())
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.contentNotFound.visibility = View.GONE
                        binding.nsvContent.visibility = View.VISIBLE
                        adapter.submitList(state.data?.data?.availableFlight)
                    }
                }

                is State.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.contentNotFound.visibility = View.VISIBLE
                    binding.nsvContent.visibility = View.GONE
                    adapter.submitList(emptyList())
                    onToast(
                        "Error!",
                        state.message.toString(),
                        StyleType.ERROR
                    )
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun datePicker() {
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            val startDateMillis = selection.first
            val endDateMillis = selection.second

            val startDateCalendar = Calendar.getInstance()
            startDateCalendar.timeInMillis = startDateMillis

            val endDateCalendar = Calendar.getInstance()
            endDateCalendar.timeInMillis = endDateMillis

            val startDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(startDateCalendar.time)
            val endDate =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(endDateCalendar.time)

            val item = ticketRequest
            viewModel.getTicket(
                TicketRequestModel(
                    item.classId,
                    item.passenger,
                    item.departureCode,
                    item.sortBy,
                    startDate,
                    item.arrivalCode,
                    endDate
                )
            )
        }

        picker.show(supportFragmentManager, picker.toString())
    }

    companion object {
        private const val EXTRA_PASSING_PEMILIHAN_TIKET: String = "EXTRA_PASSING_PEMILIHAN_TIKET"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            ticketRequest: TicketRequestModel?
        ): Intent {
            return Intent(context, PemilihanTiketActivity::class.java).apply {
                ticketRequest?.let { putExtra(EXTRA_PASSING_PEMILIHAN_TIKET, it) }
            }
        }
    }
}