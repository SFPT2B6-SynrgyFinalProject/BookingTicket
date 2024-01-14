package id.synrgy6team2.bookingticket.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.emailValid
import id.synrgy6team2.bookingticket.common.onToastError
import id.synrgy6team2.bookingticket.common.onToastInfo
import id.synrgy6team2.bookingticket.common.onToastSuccess
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.common.passwordValid
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityLoginBinding
import id.synrgy6team2.bookingticket.presentation.forgotpassword.LupaPasswordActivity
import id.synrgy6team2.bookingticket.presentation.main.MainActivity
import id.synrgy6team2.bookingticket.presentation.register.RegisterActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val data = it.data
        data?.let { intent ->
            viewModel.googleSignInFromIntent(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.login.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    onToastInfo(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description)
                    )
                }
                is State.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is State.Error -> {
                    onToastError(
                        "Error!",
                        state.message
                    )
                }
            }
        }

        viewModel.logged.observe(this) { state ->
            if (state == true) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.googleSignInFromIntent.observe(this) { state ->
            if (state is State.Success) {
                val value = LoginRequestModel(googleToken = state.data?.idToken)
                viewModel.google(value)
            }
        }
    }

    private fun bindView() {
        val verify = intent.data
        verify?.let {
            onToastSuccess(
                getString(R.string.txt_verify_successfully),
                getString(R.string.txt_verify_successfully_description)
            )
        }

        binding.btnLogin.setOnClickListener {
            onValidation(
                binding.tilEmail.emailValid(),
                binding.tilPassword.passwordValid()
            ) {
                val email = binding.txtEmail.text.toString()
                val password = binding.txtPassword.text.toString()
                val value = LoginRequestModel(email, password)
                viewModel.login(value)
            }
        }

        binding.tvForgetPassword.setOnClickListener {
            val intent = Intent(this, LupaPasswordActivity::class.java)
            startActivity(intent)
        }

        binding.tvRegisterAccount.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnGoogle.setOnClickListener {
            initiateGoogleSignIn()
        }
    }

    private fun initiateGoogleSignIn() {
        val options = createGoogleSignInOptions()
        val signInClient = GoogleSignIn.getClient(this, options)
        signInClient.signInIntent.also {
            activityForResult.launch(it)
        }
    }

    private fun createGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.host_google_oauth))
            .requestEmail()
            .build()
    }
}