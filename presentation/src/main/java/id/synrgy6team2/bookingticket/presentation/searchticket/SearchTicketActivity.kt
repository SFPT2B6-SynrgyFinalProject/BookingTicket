package id.synrgy6team2.bookingticket.presentation.searchticket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.presentation.PASSING_SEARCHING_TICKET
import id.synrgy6team2.bookingticket.presentation.PemilihanTiketActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivitySearchTicketBinding
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class SearchTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchTicketBinding
    private val viewModel: SearchTicketViewModel by viewModels()
    private var switch: Boolean = false

    private var resultLauncherFrom =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.txtFrom.setText(result.data?.getStringExtra("SEARCH_TICKET_FROM"))
                viewModel.txtAirportFrom.value =
                    result.data?.getStringExtra("SEARCH_TICKET_FROM_CODE")
            }
        }

    private var resultLauncherTo =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.txtTo.setText(result.data?.getStringExtra("SEARCH_TICKET_TO"))
                viewModel.txtAirportTo.value = result.data?.getStringExtra("SEARCH_TICKET_TO_CODE")
            }
        }

    private var resultLauncherTypeFlight =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.txtTypeFlight.setText(result.data?.getStringExtra("SEARCH_TICKET_TYPE_FLIGHT"))
                viewModel.txtTypeFlight.value =
                    result.data?.getIntExtra("SEARCH_TICKET_TYPE_FLIGHT_ID", -1)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mtSearchTicket.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.txtFrom.setOnClickListener {
            val intent = Intent(this, AirportActivity::class.java)
            resultLauncherFrom.launch(intent)
        }

        binding.txtTo.setOnClickListener {
            val intent = Intent(this, AirportActivity::class.java)
            resultLauncherTo.launch(intent)
        }

        binding.txtTypeFlight.setOnClickListener {
            val intent = Intent(this, TypeFlightActivity::class.java)
            resultLauncherTypeFlight.launch(intent)
        }

        binding.ivSwitch.setOnClickListener {
            val temporary: String
            val from = binding.txtFrom.text.toString()
            temporary = from
            val to = binding.txtTo.text.toString()
            binding.txtFrom.setText(to)
            binding.txtTo.setText(temporary)
        }

        binding.msSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                false -> {
                    binding.tilDateBack.visibility = View.GONE
                    switch = false
                }

                true -> {
                    binding.tilDateBack.visibility = View.VISIBLE
                    switch = true
                }
            }
        }

        binding.txtDateFlight.setOnClickListener {
            datePicker()
        }

        binding.txtDateBack.setOnClickListener {
            datePickerBack()
        }

        binding.btnSearchTicket.setOnClickListener {
            onValidation(
                validationParams = arrayOf(
                    binding.tilFrom to ValidationType.GENERAL,
                    binding.tilTo to ValidationType.GENERAL,
                    if (switch) {
                        binding.tilDateFlight to ValidationType.GENERAL
                        binding.tilDateBack to ValidationType.GENERAL
                    } else {
                        binding.tilDateFlight to ValidationType.GENERAL
                    },
                    binding.passenger to ValidationType.GENERAL,
                    binding.typeFlight to ValidationType.GENERAL
                ),
                onConfirmPassword = null
            ) {
                val classId = viewModel.txtTypeFlight.value
                val arrivalCode = viewModel.txtAirportFrom.value
                val destinationCode = viewModel.txtAirportTo.value
                val departureStart = viewModel.txtDatePickArrival.value
                val departureEnd = viewModel.txtDatePickDeparture.value
                val value = TicketRequestModel(
                    classId = classId,
                    passenger = TicketRequestModel.Passenger(
                        adult = binding.txtPassenger.text.toString().toInt(),
                        infant = 0,
                        child = 0
                    ),
                    departureCode = destinationCode,
                    sortBy = arrayListOf("price"),
                    departureDateStart = departureStart,
                    arrivalCode = arrivalCode,
                    departureDateEnd = departureEnd,
                )
                val intent = Intent(this, PemilihanTiketActivity::class.java)
                intent.putExtra(PASSING_SEARCHING_TICKET, value)
                startActivity(intent)
            }
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun datePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection

            val tvDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendar.time)

            binding.txtDateFlight.setText(tvDate)
            viewModel.txtDatePickArrival.value = date
        }
        picker.show(supportFragmentManager, picker.toString())
    }

    @SuppressLint("SimpleDateFormat")
    private fun datePickerBack() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection

            val tvDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendar.time)

            binding.txtDateBack.setText(tvDate)
            viewModel.txtDatePickDeparture.value = date
        }
        picker.show(supportFragmentManager, picker.toString())
    }

}