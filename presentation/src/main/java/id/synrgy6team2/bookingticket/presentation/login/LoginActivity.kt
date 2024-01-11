package id.synrgy6team2.bookingticket.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.emailValid
import id.synrgy6team2.bookingticket.common.passwordValid
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityLoginBinding
import id.synrgy6team2.bookingticket.presentation.forgotpassword.LupaPasswordActivity
import id.synrgy6team2.bookingticket.presentation.main.MainActivity
import id.synrgy6team2.bookingticket.presentation.register.RegisterActivity
import io.github.anderscheow.validator.Validator
import io.github.anderscheow.validator.constant.Mode
import io.github.anderscheow.validator.validator

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
        viewModel.logged()

        viewModel.login.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(this, getString(R.string.txt_loading_progress), Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is State.Error -> {
                    Toast.makeText(this, state.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.logged.observe(this) { state ->
            if (state is State.Success) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.googleSignInFromIntent.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    Toast.makeText(this, getString(R.string.txt_loading_progress), Toast.LENGTH_SHORT).show()
                }
                is State.Success -> {
                    val value = LoginRequestModel(googleToken = state.data?.idToken)
                    viewModel.google(value)
                }
                is State.Error -> {
                    Toast.makeText(this, state.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun bindView() {
        val verify = intent.data
        verify?.let {
            Toast.makeText(this, getString(R.string.txt_verify_successfully), Toast.LENGTH_LONG).show()
        }

        binding.btnLogin.setOnClickListener {
            onValidation()
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

    private fun onValidation() {
        validator(this) {
            mode = Mode.SINGLE
            listener = onLogin()
            validate(
                binding.tilEmail.emailValid(),
                binding.tilPassword.passwordValid()
            )
        }
    }

    private fun onLogin() = object : Validator.OnValidateListener {
        override fun onValidateFailed(errors: List<String>) {}

        override fun onValidateSuccess(values: List<String>) {
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val value = LoginRequestModel(email, password)
            viewModel.login(value)
        }
    }
}