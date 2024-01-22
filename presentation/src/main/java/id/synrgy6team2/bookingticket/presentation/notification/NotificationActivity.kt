package id.synrgy6team2.bookingticket.presentation.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.R
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityNotificationBinding
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    @Inject lateinit var adapterNotificationAdapter: NotificationAdapter
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
        bindAdapterNotification()
    }

    private fun bindAdapterNotification() {
        binding.rvToday.setHasFixedSize(false)
        binding.rvToday.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvToday.itemAnimator = DefaultItemAnimator()
        binding.rvToday.isNestedScrollingEnabled = false
        binding.rvToday.adapter = adapterNotificationAdapter

        adapterNotificationAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterNotificationAdapter.onClick { _, item -> onToast(item.description, "On Cliked!", StyleType.INFO) }
    }
}