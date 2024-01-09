package id.synrgy6team2.bookingticket.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityRegisterBinding
import java.util.Calendar

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tieDateOfBirth.setOnClickListener {
            datePicker()
        }


    }

    private fun datePicker() {
        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, _, monthOfYear, dayOfMonth ->
                val date = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                binding.tilDateOfBirth.editText?.setText(date)
            },
            year, month, day
        )
        datePickerDialog.show()
    }


}