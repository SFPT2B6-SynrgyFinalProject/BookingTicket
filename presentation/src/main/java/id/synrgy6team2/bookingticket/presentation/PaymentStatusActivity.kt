package id.synrgy6team2.bookingticket.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPaymentStatusBinding

class PaymentStatusActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnOrderDetail.setOnClickListener {
            val intent = Intent(this, BookingDetailActivity::class.java)
            startActivity(intent)
        }
    }
}