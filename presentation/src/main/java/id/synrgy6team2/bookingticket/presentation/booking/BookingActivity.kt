package id.synrgy6team2.bookingticket.presentation.booking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.PaymentActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityBookingBinding

@AndroidEntryPoint
class BookingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookingBinding
    private val classId: Int by lazy { intent.getIntExtra(EXTRA_PASSING_CLASS_ID, 0) }
    private val ticketId: Int by lazy { intent.getIntExtra(EXTRA_PASSING_TICKET_ID, 0) }
    private val passengerCount: Int by lazy { intent.getIntExtra(EXTRA_PASSING_PASSENGER_COUNT, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val EXTRA_PASSING_CLASS_ID: String = "EXTRA_PASSING_CLASS_ID"
        private const val EXTRA_PASSING_TICKET_ID: String = "EXTRA_PASSING_TICKET_ID"
        private const val EXTRA_PASSING_PASSENGER_COUNT: String = "EXTRA_PASSING_PASSENGER_COUNT"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            classId: Int?,
            ticketId: Int?,
            passengerCount: Int?
        ): Intent {
            return Intent(context, BookingActivity::class.java).apply {
                classId?.let { putExtra(EXTRA_PASSING_CLASS_ID, it) }
                ticketId?.let { putExtra(EXTRA_PASSING_TICKET_ID, it) }
                passengerCount?.let { putExtra(EXTRA_PASSING_PASSENGER_COUNT, it) }
            }
        }
    }
}