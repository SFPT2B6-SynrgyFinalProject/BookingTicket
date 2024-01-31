package id.synrgy6team2.bookingticket.presentation.payment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.synrgy6team2.bookingticket.presentation.PaymentStatusActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPaymentBinding

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            val intent = Intent(this, PaymentStatusActivity::class.java)
            startActivity(intent)
        }
    }
}