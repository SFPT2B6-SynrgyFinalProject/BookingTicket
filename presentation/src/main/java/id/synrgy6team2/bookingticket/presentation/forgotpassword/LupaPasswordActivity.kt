package id.synrgy6team2.bookingticket.presentation.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.emailValid
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityLupaPasswordBinding
import id.synrgy6team2.bookingticket.presentation.resetpassword.ResetPasswordActivity

@AndroidEntryPoint
class LupaPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLupaPasswordBinding
    private val viewModel: LupaPasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }
    private  fun bindObserver(){
        viewModel.lupaPassword.observe(this){ state ->
            when (state){
                is State.Loading ->{
                    onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success ->{
                    val intent = Intent(this, ResetPasswordActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                is State.Error ->{
                    onToast(
                        "Error!",
                        state.message,
                        StyleType.ERROR
                    )
                }
            }

        }
    }

    private fun bindView(){
        binding.btnSend.setOnClickListener{
            binding.tilEmail.emailValid()
            val email = binding.txtEmail.text.toString()
            val value = ForgotPasswordRequestModel(email)
            viewModel.lupaPassword(value)
        }
    }
}