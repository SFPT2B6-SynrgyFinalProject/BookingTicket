package id.synrgy6team2.bookingticket.presentation.resetpassword

import android.content.Intent
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
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityResetPasswordBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.resetPassword.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }

                is State.Success -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                is State.Error -> {
                    onToast(
                        "Error!",
                        state.message,
                        StyleType.ERROR
                    )
                }
            }
        }
    }

    private fun bindView() {
        binding.btnResetPassword.setOnClickListener {
            onValidation(
                validationParams = arrayOf(
                    binding.tvNewPassword to ValidationType.PASSWORD,
                    binding.tvConfirmNewPassword to ValidationType.CONFIRM_PASSWORD
                ),
                onConfirmPassword = binding.txtNewPassword
            ) {
                val newPassword = binding.txtNewPassword.text.toString()
                val otp = intent.data?.lastPathSegment
                val value = ResetPasswordRequestModel(otp, newPassword)
                viewModel.resetPassword(value)
            }
        }
    }
}