package id.synrgy6team2.bookingticket.presentation.resetpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.confirmPasswordValid
import id.synrgy6team2.bookingticket.common.passwordValid
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityResetPasswordBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

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
                    onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        MotionToastStyle.INFO
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
                        MotionToastStyle.ERROR
                    )
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

    private fun onToast(title: String?, message: String?, style: MotionToastStyle) {
        MotionToast.createColorToast(
            this,
            title ?: "",
            message ?: "",
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.main_font_family))
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