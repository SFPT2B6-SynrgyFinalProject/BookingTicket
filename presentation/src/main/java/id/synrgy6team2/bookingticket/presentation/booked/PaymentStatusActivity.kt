package id.synrgy6team2.bookingticket.presentation.booked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.synrgy6team2.bookingticket.presentation.MainActivity
import id.synrgy6team2.bookingticket.presentation.bookingdetail.BookingDetailActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPaymentStatusBinding
import id.synrgy6team2.bookingticket.presentation.payment.PaymentActivity

class PaymentStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentStatusBinding

    private val orderId: String by lazy { intent.getStringExtra(EXTRA_PASSING_ORDER_ID) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnOrderDetail.setOnClickListener {
            val intent = BookingDetailActivity.getIntentTo(this, orderId, true)
            startActivity(intent)
        }
    }

    companion object {
        private const val EXTRA_PASSING_ORDER_ID: String = "EXTRA_PASSING_ORDER_ID"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            orderId: String?
        ): Intent {
            return Intent(context, PaymentStatusActivity::class.java).apply {
                putExtra(EXTRA_PASSING_ORDER_ID, orderId)
            }
        }
    }
}