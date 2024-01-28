package id.synrgy6team2.bookingticket.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.parcelable
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityPemilihanTiketBinding

const val PASSING_SEARCHING_TICKET: String = "PASSING_SEARCHING_TICKET"

class PemilihanTiketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPemilihanTiketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPemilihanTiketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent.parcelable<TicketRequestModel>(PASSING_SEARCHING_TICKET)
        createMessageDialog(
            "Passing Data",
            "Mendapatkan data dari halaman Pencarian Ticket, ini untuk data pencarian:" +
                    "${item}"
        ) { it.dismiss() }

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.contentNotFound.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            startActivity(intent)
        }
    }
}