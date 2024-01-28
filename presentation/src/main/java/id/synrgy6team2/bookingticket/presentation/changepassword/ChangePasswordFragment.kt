package id.synrgy6team2.bookingticket.presentation.changepassword

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentChangePasswordBinding

@AndroidEntryPoint
class ChangePasswordFragment : BottomSheetDialogFragment() {

    private val viewModel: ChangePasswordViewModel by viewModels()
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnChangePassword.setOnClickListener {
            requireContext().onValidation(
                validationParams = arrayOf(
                    binding.inputCurrentPassword to ValidationType.PASSWORD,
                    binding.inputNewPassword to ValidationType.PASSWORD,
                    binding.inputConfirmPassword to ValidationType.CONFIRM_PASSWORD
                ),
                onConfirmPassword = binding.txtNewPassword
            ) {
                val current = binding.txtCurrentPassword.text.toString()
                val new = binding.txtNewPassword.text.toString()
                val confirm = binding.txtConfirmPassword.text.toString()
                val value = ChangePasswordRequestModel(current, new, confirm)
                viewModel.changePassword(value)
            }
        }
    }

    private fun bindObserver() {
        viewModel.changePassword.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    requireActivity().onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success -> {
                    dismiss()
                }
                is State.Error -> {
                    requireActivity().onToast(
                        "Error!",
                        it.message,
                        StyleType.ERROR
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}