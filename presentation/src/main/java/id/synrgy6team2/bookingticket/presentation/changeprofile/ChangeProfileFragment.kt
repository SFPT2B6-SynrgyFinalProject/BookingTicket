package id.synrgy6team2.bookingticket.presentation.changeprofile

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.FragmentChangeProfileBinding
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class ChangeProfileFragment : BottomSheetDialogFragment() {

    private val viewModel: ChangeProfileViewModel by viewModels()
    private var _binding: FragmentChangeProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.txtBirtDate.setOnClickListener {
            datePicker()
        }

        binding.btnChangePassword.setOnClickListener {
            val destination = ChangeProfileFragmentDirections.actionChangeProfileFragmentToChangePasswordFragment()
            findNavController().navigate(destination)
        }

        binding.btnChangeProfile.setOnClickListener {
            requireContext().onValidation(
                validationParams = arrayOf(
                    binding.inputFullname to ValidationType.GENERAL,
                    binding.inputEmail to ValidationType.EMAIL,
                    binding.inputGender to ValidationType.GENERAL,
                    binding.inputBirthdate to ValidationType.GENERAL,
                    binding.inputPhone to ValidationType.GENERAL
                ),
                onConfirmPassword = null
            ) {
                val email = binding.txtEmail.text.toString()
                val fullName = binding.txtFullName.text.toString()
                val noHp = binding.txtPhone.text.toString()
                val birthDate = viewModel.saveStateBirthDate.value
                val gender = binding.txtGender.text.toString()
                val value = UpdateUserRequestModel(email, fullName, noHp, birthDate, gender)
                viewModel.changeProfile(value)
            }
        }
    }

    private fun bindObserver() {
        viewModel.showProfile.observe(viewLifecycleOwner) {
            binding.txtEmail.setText(it.data?.email ?: "")
            binding.txtFullName.setText(it.data?.fullName ?: "")
            binding.txtPhone.setText(it.data?.noHp ?: "")

            /**
             * value birth date
             * */
            it.data?.birthDate?.let { dateTime ->
                val calendar = Calendar.getInstance()
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val parsedDate = sdf.parse(dateTime)
                if (parsedDate != null) {
                    calendar.time = parsedDate
                }
                val tvDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendar.time)
                binding.txtBirtDate.setText(tvDate)
                viewModel.saveStateBirthDate.value = date
            }

            /**
             * value gender
             * */
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.gender)
            )
            binding.txtGender.setAdapter(adapter)
            it.data?.gender?.let { selectedGender ->
                val position = adapter.getPosition(selectedGender)
                if (position != -1) {
                    binding.txtGender.setText(selectedGender, false)
                }
            }
        }

        viewModel.changeProfile.observe(viewLifecycleOwner) {
            when (it) {
                is StateLocal.Loading -> {
                    requireActivity().onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is StateLocal.Success -> {
                    requireContext().createMessageDialog(
                        getString(R.string.txt_verify_successfully),
                        getString(R.string.txt_change_profile_successfully)
                    ) { dialogInterface: DialogInterface -> dialogInterface.dismiss() }
                }
                is StateLocal.Error -> {
                    requireActivity().onToast(
                        "Error!",
                        it.error?.message,
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

    @SuppressLint("SimpleDateFormat")
    private fun datePicker() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection

            val tvDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendar.time)

            binding.txtBirtDate.setText(tvDate)
            viewModel.saveStateBirthDate.value = date
        }
        picker.show(childFragmentManager, picker.toString())
    }
}