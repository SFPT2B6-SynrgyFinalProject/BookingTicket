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
import id.synrgy6team2.bookingticket.presentation.databinding.ActivitySearchTicketBinding
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class SearchTicketActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchTicketBinding

    private var switch: Boolean = false

    private val viewModel: AirportViewModel by viewModels()

    private var resultLauncherFrom = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.txtFrom.setText(result.data?.getStringExtra("SEARCH_TICKET_FROM"))
        }
    }

    private var resultLauncherTo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.txtTo.setText(result.data?.getStringExtra("SEARCH_TICKET_TO"))
        }
    }

    private var resultLauncherTypeFlight = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.txtTypeFlight.setText(result.data?.getStringExtra("SEARCH_TICKET_"))
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

        binding.msSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
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
//            viewModel.saveStateBirthDate.value = date
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
//            viewModel.saveStateBirthDate.value = date
        }
        picker.show(supportFragmentManager, picker.toString())
    }

}