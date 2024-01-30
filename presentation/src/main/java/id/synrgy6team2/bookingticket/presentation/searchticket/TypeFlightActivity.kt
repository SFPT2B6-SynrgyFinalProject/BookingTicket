package id.synrgy6team2.bookingticket.presentation.searchticket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityTypeFlightBinding
import javax.inject.Inject

@AndroidEntryPoint
class TypeFlightActivity : AppCompatActivity() {

    @Inject lateinit var adapter: TypeFlightAdapter
    private lateinit var binding: ActivityTypeFlightBinding
    private val viewModel: TypeFlightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTypeFlightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvContent.adapter = adapter

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.flightClass.observe(this) { result ->
            if (result.data?.isNotEmpty() == true) {
                result.data?.let { adapter.updateData(it) }
                binding.contentNotFound.isVisible = false
            } else {
                adapter.updateData(listOf())
                binding.contentNotFound.isVisible = true
            }
        }

        adapter.onClick { _, item ->
            val intent = Intent(this, SearchTicketActivity::class.java)
            intent.putExtra("SEARCH_TICKET_TYPE_FLIGHT", item.name)
            intent.putExtra("SEARCH_TICKET_TYPE_FLIGHT_ID", item.id)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }


}