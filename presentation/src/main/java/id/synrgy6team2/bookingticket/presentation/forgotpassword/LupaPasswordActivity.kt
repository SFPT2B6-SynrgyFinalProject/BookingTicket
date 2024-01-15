package id.synrgy6team2.bookingticket.presentation.forgotpassword

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityLupaPasswordBinding

@AndroidEntryPoint
class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding
    private val viewModel: LupaPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindView() {
        binding.btnSend.setOnClickListener {
            onValidation(
                validationParams = arrayOf(
                    binding.tilEmail to ValidationType.EMAIL
                ), onConfirmPassword = null
            ) {
                val email = binding.txtEmail.text.toString()
                val value = ForgotPasswordRequestModel(email = email)
                viewModel.forgetPassword(value)
            }
        }
    }

    private fun bindObserver() {
        viewModel.forgetPassword.observe(this) { state ->
            if (state is State.Loading) {
                onToast(
                    getString(R.string.txt_loading_progress),
                    getString(R.string.txt_loading_progress_description),
                    StyleType.INFO
                )
            }

            if (state is State.Success) {
                onToast(
                    getString(R.string.txt_verify_successfully),
                    getString(R.string.txt_forgot_password_successfully) + " ${state.data?.data?.email}",
                    StyleType.SUCCESS,
                    8000L
                )
            }

            if (state is State.Error) {
                onToast(
                    "Error!",
                    state.message,
                    StyleType.ERROR
                )
            }
        }
    }
}