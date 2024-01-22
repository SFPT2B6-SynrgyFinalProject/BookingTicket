package id.synrgy6team2.bookingticket.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentDashboardBinding
import id.synrgy6team2.bookingticket.presentation.notification.NotificationActivity
import javax.inject.Inject

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    @Inject lateinit var adapterMainMenuAdapter: DashboardMainMenuAdapter
    @Inject lateinit var adapterRecomendedAdapter: DashboardRecomendedAdapter
    @Inject lateinit var adapterHistoryAdapter: DashboardHistoryAdapter
    @Inject lateinit var adapterPopularAdapter: DashboardPopularAdapter
    private val viewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            requireActivity().finish()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.mainMenu.observe(viewLifecycleOwner) {
            adapterMainMenuAdapter.submitList(it)
        }
        viewModel.recomended.observe(viewLifecycleOwner) {
            adapterRecomendedAdapter.submitList(it)
        }
        viewModel.history.observe(viewLifecycleOwner) {
            adapterHistoryAdapter.submitList(it)
        }
        viewModel.popular.observe(viewLifecycleOwner) {
            adapterPopularAdapter.submitList(it)
        }
    }

    private fun bindView() {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        binding.btnNotification.setOnClickListener {
            val intent = Intent(requireActivity(), NotificationActivity::class.java)
            startActivity(intent)
        }
        bindAdapterMainMenu()
        bindAdapterRecomended()
        bindAdapterHistory()
        bindAdapterPopular()
    }

    private fun bindAdapterPopular() {
        binding.rvHomePopularDestination.setHasFixedSize(false)
        binding.rvHomePopularDestination.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvHomePopularDestination.itemAnimator = DefaultItemAnimator()
        binding.rvHomePopularDestination.isNestedScrollingEnabled = false
        binding.rvHomePopularDestination.adapter = adapterPopularAdapter

        adapterPopularAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterPopularAdapter.onClick { _, item -> requireActivity().onToast(item.title, "On Cliked!", StyleType.INFO) }
    }

    private fun bindAdapterHistory() {
        binding.rvHomeHistorySearch.setHasFixedSize(false)
        binding.rvHomeHistorySearch.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvHomeHistorySearch.itemAnimator = DefaultItemAnimator()
        binding.rvHomeHistorySearch.isNestedScrollingEnabled = false
        binding.rvHomeHistorySearch.adapter = adapterHistoryAdapter

        adapterHistoryAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterHistoryAdapter.onClick { _, item -> requireActivity().onToast(item.schedule, "On Cliked!", StyleType.INFO) }
    }

    private fun bindAdapterRecomended() {
        binding.rvHomeRecomendedBooking.setHasFixedSize(false)
        binding.rvHomeRecomendedBooking.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvHomeRecomendedBooking.itemAnimator = DefaultItemAnimator()
        binding.rvHomeRecomendedBooking.isNestedScrollingEnabled = false
        binding.rvHomeRecomendedBooking.adapter = adapterRecomendedAdapter

        adapterRecomendedAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterRecomendedAdapter.onClick { _, item -> requireActivity().onToast(item.title, "On Cliked!", StyleType.INFO) }
    }

    private fun bindAdapterMainMenu() {
        binding.rvHomeMenuMain.setHasFixedSize(false)
        binding.rvHomeMenuMain.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvHomeMenuMain.itemAnimator = DefaultItemAnimator()
        binding.rvHomeMenuMain.isNestedScrollingEnabled = false
        binding.rvHomeMenuMain.adapter = adapterMainMenuAdapter

        adapterMainMenuAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterMainMenuAdapter.onClick { _, item -> requireActivity().onToast(item.title, "On Cliked!", StyleType.INFO) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}