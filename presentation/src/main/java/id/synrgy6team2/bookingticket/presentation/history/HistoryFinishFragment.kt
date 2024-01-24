package id.synrgy6team2.bookingticket.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentHistoryFinishBinding
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFinishFragment : Fragment() {

    @Inject lateinit var adapterHistoryChildAdapter: HistoryChildAdapter
    private val viewModel: HistoryViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )
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
        binding.rvHistoryFinish.setHasFixedSize(false)
        binding.rvHistoryFinish.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvHistoryFinish.itemAnimator = DefaultItemAnimator()
        binding.rvHistoryFinish.isNestedScrollingEnabled = false
        binding.rvHistoryFinish.adapter = adapterHistoryChildAdapter

        adapterHistoryChildAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterHistoryChildAdapter.onClick { _, _ -> requireActivity().onToast("Title", "On Cliked!", StyleType.INFO) }
    }

    private fun bindObserver() {
        viewModel.cancelHistory.observe(viewLifecycleOwner) {
            adapterHistoryChildAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}