package id.synrgy6team2.bookingticket.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentProfileBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    @Inject
    lateinit var adapterProfileAdapter: ProfileAdapter
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
        viewModel.profile.observe(viewLifecycleOwner) { it: ProfileResponseModel ->
            binding.layoutMyAccount.tvMyAccount.text = it.data?.fullName ?: getString(R.string.akun_saya)
            binding.layoutMyAccount.tvDetailAccount.text = it.data?.email ?: getString(R.string.masuk_melalui_google)
        }

        viewModel.profileSettings.observe(viewLifecycleOwner) { it: List<ProfileModel> ->
            adapterProfileAdapter.submitList(it)
        }

        viewModel.logged.observe(viewLifecycleOwner) { state: Boolean ->
            if (!state) {
                binding.layoutMyAccount.root.setOnClickListener {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun bindView() {
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
        bindProfileAdapter()
        bindChangeProfile()
    }

    private fun bindChangeProfile() {
        binding.layoutMyAccount.root.setOnClickListener {
            val destination = ProfileFragmentDirections.actionProfileFragmentToChangeProfileFragment()
            findNavController().navigate(destination)
        }
    }

    private fun bindProfileAdapter() {
        binding.rvProfile.setHasFixedSize(false)
        binding.rvProfile.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvProfile.itemAnimator = DefaultItemAnimator()
        binding.rvProfile.isNestedScrollingEnabled = false
        binding.rvProfile.adapter = adapterProfileAdapter

        adapterProfileAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterProfileAdapter.onClick { _, item ->
            if (item.id == "logout") {
                requireActivity().createMessageDialog(
                    getString(R.string.txt_title_logout),
                    getString(R.string.txt_message_logout),
                    onItemPositive = { initiateGoogleSignOut() },
                    onItemNegative = { it.dismiss() }
                )
            }
        }
    }

    private fun initiateGoogleSignOut() {
        val options = createGoogleSignInOptions()
        val signInClient = GoogleSignIn.getClient(requireActivity(), options)
        viewModel.logout(signInClient)
    }

    private fun createGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}