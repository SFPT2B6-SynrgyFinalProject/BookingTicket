package id.synrgy6team2.bookingticket.presentation.searchticket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityAirportBinding
import javax.inject.Inject

@AndroidEntryPoint
class AirportActivity : AppCompatActivity() {

    @Inject lateinit var adapter: AirportAdapter
    private lateinit var binding: ActivityAirportBinding
    private val viewModel: AirportViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContent.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.txtSearch.addTextChangedListener {
            viewModel.airport(it.toString())
        }

        viewModel.airport(null)

        adapter.onClick { _, item ->
            val intent = Intent(this, SearchTicketActivity::class.java)
            intent.putExtra("SEARCH_TICKET_FROM", item.airportName)
            intent.putExtra("SEARCH_TICKET_FROM_CODE", item.code)
            intent.putExtra("SEARCH_TICKET_TO", item.airportName)
            intent.putExtra("SEARCH_TICKET_TO_CODE", item.code)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        viewModel.airport.observe(this) { result ->
            if (result.data?.isNotEmpty() == true) {
                result.data?.let { adapter.updateData(it) }
                binding.contentNotFound.isVisible = false
            } else {
                binding.contentNotFound.isVisible = true
            }
        }

    }
}