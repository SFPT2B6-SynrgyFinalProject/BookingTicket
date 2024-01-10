package id.synrgy6team2.bookingticket.presentation.resetpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.confirmPasswordValid
import id.synrgy6team2.bookingticket.common.passwordValid
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityResetPasswordBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

@AndroidEntryPoint
class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.resetPassword.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(
                        this,
                        getString(R.string.txt_loading_progress),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is State.Success -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                is State.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindView() {
        binding.btnResetPassword.setOnClickListener {
            onValidation()
        }
    }

    private fun onValidation() {
        validator(this) {
            mode = Mode.SINGLE
            listener = onReset()
            validate(
                binding.tvNewPassword.passwordValid(),
                binding.tvConfirmNewPassword.confirmPasswordValid(binding.txtNewPassword)
            )
        }
    }

    private fun onReset() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) {}

        override fun onValidateSuccess(values: List<String>) {
            val newPassword = binding.txtNewPassword.text.toString()
            val otp = intent.data?.lastPathSegment
            val value = ResetPasswordRequestModel(otp, newPassword)
            viewModel.resetPassword(value)
        }
    }
}