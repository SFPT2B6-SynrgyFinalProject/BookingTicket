package id.synrgy6team2.bookingticket.presentation.login

import android.app.Activity
import android.content.DialogInterface
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
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.createMessageDialog
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityLoginBinding
import id.synrgy6team2.bookingticket.presentation.forgotpassword.LupaPasswordActivity
import id.synrgy6team2.bookingticket.presentation.MainActivity
import id.synrgy6team2.bookingticket.presentation.register.RegisterActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.let { intent ->
            viewModel.googleSignInFromIntent(intent)
        }
    }

    private val activityForResultPassing = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { intent ->
                this.createMessageDialog(
                    getString(R.string.txt_verify_successfully),
                    getString(R.string.txt_register_has_beed_success) + " ${intent.getStringExtra("SUCCESS")}"
                ) { dialogInterface: DialogInterface -> dialogInterface.dismiss() }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
        viewModel.verify.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    onToast(
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_loading_progress),
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success -> {
                    createMessageDialog(
                        getString(id.synrgy6team2.bookingticket.common.R.string.txt_verify_successfully),
                        getString(R.string.txt_verify_successfully_description)
                    ) { dialogInterface: DialogInterface -> dialogInterface.dismiss() }
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

        viewModel.login.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    onToast(
                        getString(R.string.txt_loading_progress),
                        getString(R.string.txt_loading_progress_description),
                        StyleType.INFO
                    )
                }
                is State.Success -> {
                    val intent = Intent(this, MainActivity::class.java)
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
        intent.data?.let { uri ->
            viewModel.verify(uri.lastPathSegment)
        }

        binding.btnLogin.setOnClickListener {
            onValidation(
                validationParams = arrayOf(
                    binding.tilEmail to ValidationType.EMAIL,
                    binding.tilPassword to ValidationType.PASSWORD
                ),
                onConfirmPassword = null
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
            activityForResultPassing.launch(intent)
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