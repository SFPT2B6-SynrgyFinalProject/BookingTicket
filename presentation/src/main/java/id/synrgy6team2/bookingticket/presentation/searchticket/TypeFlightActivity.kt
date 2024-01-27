package id.synrgy6team2.bookingticket.presentation.searchticket

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityTypeFlightBinding

@AndroidEntryPoint
class TypeFlightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTypeFlightBinding

    private val viewModel: TypeFlightViewModel by viewModels()

    private val adapter by lazy { TypeFlightAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContent.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.flightClass.observe(this) { result ->
            result.data?.let { adapter.updateData(it) }
        }
    }


}