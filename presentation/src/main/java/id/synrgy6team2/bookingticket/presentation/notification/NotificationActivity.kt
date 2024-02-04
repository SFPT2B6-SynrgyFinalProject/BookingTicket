package id.synrgy6team2.bookingticket.presentation.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import id.synrgy6team2.bookingticket.presentation.MainActivity
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityNotificationBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class NotificationActivity : AppCompatActivity() {

    @Inject
    lateinit var adapterNotificationAdapter: NotificationAdapter
    private lateinit var binding: ActivityNotificationBinding
    private val viewModel: NotificationViewModel by viewModels()
    private val modeFlags: Boolean by lazy { intent.getBooleanExtra(EXTRA_PASSING_MODE_FLAGAS, false) }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (modeFlags) {
                val intent = Intent(this@NotificationActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
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
        viewModel.getNotification.onEach { state ->
            handleStateOrderDetail(state, {
                binding.progressBar.isVisible = true
                binding.contentNotFound.isVisible = false
                adapterNotificationAdapter.submitList(emptyList())
            }, {
                binding.progressBar.isVisible = false
                binding.contentNotFound.isVisible = it?.isEmpty() == true
                adapterNotificationAdapter.submitList(it)
            }, { message ->
                binding.progressBar.isVisible = false
                binding.contentNotFound.isVisible = true
                adapterNotificationAdapter.submitList(emptyList())
                onToast("Error!", message, StyleType.ERROR)
            })
        }.launchIn(lifecycleScope)
        viewModel.getNotification()
    }

    private fun handleStateOrderDetail(
        state: State<List<NotificationResponseModel.Data.NotificationItem>>,
        onLoading: () -> Unit,
        onSuccess: (List<NotificationResponseModel.Data.NotificationItem>?) -> Unit,
        onError: (String?) -> Unit) {
        when (state) {
            is State.Loading -> { onLoading.invoke() }
            is State.Success -> { onSuccess.invoke(state.data) }
            is State.Error -> { onError.invoke(state.message) }
        }
    }

    private fun bindView() {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bindAppBar()
        bindAdapterNotification()
    }

    private fun bindAppBar() {
        binding.topAppBar.setNavigationIcon(R.drawable.ic_back)
        binding.topAppBar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
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

        adapterNotificationAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    companion object {
        private const val EXTRA_PASSING_MODE_FLAGAS: String = "EXTRA_PASSING_MODE_FLAGS"

        @JvmStatic
        fun getIntentTo(
            context: Context?,
            modeFlags: Boolean?
        ): Intent {
            return Intent(context, NotificationActivity::class.java).apply {
                putExtra(EXTRA_PASSING_MODE_FLAGAS, modeFlags)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        }
    }
}