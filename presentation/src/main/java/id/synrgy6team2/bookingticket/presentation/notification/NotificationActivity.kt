package id.synrgy6team2.bookingticket.presentation.notification

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityNotificationBinding
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    @Inject
    lateinit var adapterNotificationAdapter: NotificationAdapter
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.notification.observe(this) {
            adapterNotificationAdapter.submitList(it)
        }
    }

    private fun bindView() {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bindAppBar()
        bindAdapterNotification()
    }

    private fun bindAppBar() {
        binding.topAppBar.setNavigationIcon(R.drawable.ic_back)
        binding.topAppBar.setNavigationOnClickListener { finish() }
        binding.topAppBar.menu.findItem(id.synrgy6team2.bookingticket.presentation.R.id.setting)
            .setOnMenuItemClickListener {
                onToast("Setting", "Lorem ipsum", StyleType.INFO)
                false
            }
    }

    private fun bindAdapterNotification() {
        binding.rvToday.setHasFixedSize(false)
        binding.rvToday.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvToday.itemAnimator = DefaultItemAnimator()
        binding.rvToday.isNestedScrollingEnabled = false
        binding.rvToday.adapter = adapterNotificationAdapter

        adapterNotificationAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterNotificationAdapter.onClick { _, item ->
            onToast(
                item.description,
                "On Cliked!",
                StyleType.INFO
            )
        }
    }
}