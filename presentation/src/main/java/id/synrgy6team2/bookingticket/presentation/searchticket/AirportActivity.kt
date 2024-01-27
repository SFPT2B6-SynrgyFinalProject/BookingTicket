package id.synrgy6team2.bookingticket.presentation.searchticket

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityAirportBinding

@AndroidEntryPoint
class AirportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAirportBinding

    private val viewModel: AirportViewModel by viewModels()

    private val adapter by lazy { AirportAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContent.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        adapter.onClick { position, item ->
            viewModel.selectedAirport.value = item
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.airport.observe(this) { result ->
            result.data?.let { adapter.updateData(it) }
        }

    }
}