package id.synrgy6team2.bookingticket.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.presentation.bookingdetail.BookingDetailActivity
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentHistoryFinishBinding
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFinishFragment : Fragment() {

    @Inject lateinit var adapterHistoryChildAdapter: HistoryChildAdapter
    private val viewModel: HistoryViewModel by viewModels()
    private var _binding: FragmentHistoryFinishBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnRetry.setOnClickListener { viewModel.getOrder("COMPLETED") }

        binding.rvHistoryFinish.setHasFixedSize(false)
        binding.rvHistoryFinish.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvHistoryFinish.itemAnimator = DefaultItemAnimator()
        binding.rvHistoryFinish.isNestedScrollingEnabled = true
        binding.rvHistoryFinish.adapter = adapterHistoryChildAdapter

        adapterHistoryChildAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterHistoryChildAdapter.onClick { _, item ->
            val intent = BookingDetailActivity.getIntentTo(requireContext(), item.orderId)
            startActivity(intent)
        }
    }

    private fun bindObserver() {
        viewModel.getOrder.observe(viewLifecycleOwner) { state ->
            handleStatePaymentOrder(state, {
                binding.progressBar.isVisible = true
                binding.contentNotFound.isVisible = false
            }, { data ->
                binding.progressBar.isVisible = false
                binding.contentNotFound.isVisible = false
                adapterHistoryChildAdapter.submitList(data?.data?.orders)
            }, { message ->
                binding.progressBar.isVisible = false
                binding.contentNotFound.isVisible = true
                requireActivity().onToast("Error!", message, StyleType.ERROR)
            })
        }
        viewModel.getOrder("COMPLETED")
    }

    private fun handleStatePaymentOrder(
        state: State<GetOrderResponseModel>,
        onLoading: () -> Unit,
        onSuccess: (GetOrderResponseModel?) -> Unit,
        onError: (String?) -> Unit) {
        when (state) {
            is State.Loading -> { onLoading.invoke() }
            is State.Success -> { onSuccess.invoke(state.data) }
            is State.Error -> { onError.invoke(state.message) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}