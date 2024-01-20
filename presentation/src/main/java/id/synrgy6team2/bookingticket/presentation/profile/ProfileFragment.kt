package id.synrgy6team2.bookingticket.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentProfileBinding
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject lateinit var adapterProfileAdapter: ProfileAdapter
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.profileSettings.observe(viewLifecycleOwner) {
            adapterProfileAdapter.submitList(it)
        }
    }

    private fun bindView() {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bindProfileAdapter()
    }

    private fun bindProfileAdapter() {
        binding.rvProfile.setHasFixedSize(false)
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.isNestedScrollingEnabled = false
        binding.rvProfile.adapter = adapterProfileAdapter

        adapterProfileAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterProfileAdapter.onClick { _, item -> requireActivity().onToast(item.id, "On Cliked!", StyleType.INFO) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}