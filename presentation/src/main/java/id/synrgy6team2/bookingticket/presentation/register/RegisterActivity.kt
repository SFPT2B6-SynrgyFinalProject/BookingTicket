package id.synrgy6team2.bookingticket.presentation.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityRegisterBinding
import id.synrgy6team2.bookingticket.presentation.login.LoginActivity
import id.synrgy6team2.bookingticket.presentation.main.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val data = it.data
        data?.let { intent ->
            viewModel.googleSignInFromIntent(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
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

        viewModel.register.observe(this) { state ->
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

        viewModel.googleSignInFromIntent.observe(this) { state ->
            if (state is State.Success) {
                val value = LoginRequestModel(googleToken = state.data?.idToken)
                viewModel.google(value)
            }
        }
    }

    private fun bindView() {
        binding.btnRegister.setOnClickListener {
            onValidation(
                validationParams = arrayOf(
                    binding.tilEmail to ValidationType.EMAIL,
                    binding.tilFullName to ValidationType.GENERAL,
                    binding.tilPassword to ValidationType.PASSWORD,
                    binding.tilDateOfBirth to ValidationType.GENERAL,
                    binding.tilGender to ValidationType.GENERAL
                ),
                onConfirmPassword = null
            ) {
                val email = binding.txtEmail.text.toString()
                val fullName = binding.txtFullName.text.toString()
                val password = binding.txtPassword.text.toString()
                val birthDay = viewModel.saveStateBirthDate.value
                val gender = binding.txtAutoComplete.text.toString()
                val value = RegisterRequestModel(
                    email, password, fullName, birthDay, gender
                )
                viewModel.register(value)
            }
        }

        binding.btnGoogle.setOnClickListener {
            initiateGoogleSignIn()
        }

        binding.tvLoginAccount.setOnClickListener {
            finish()
        }

        binding.tieDateOfBirth.setOnClickListener {
            datePicker()
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

    @SuppressLint("SimpleDateFormat")
    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this, { _, _, monthOfYear, dayOfMonth ->
                val tvDate = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(
                    c.apply {
                        set(year, monthOfYear, dayOfMonth, 0, 0, 0)
                    }.time
                )
                binding.tieDateOfBirth.setText(tvDate)
                viewModel.saveStateBirthDate.value = date
            }, year, month, day
        )
        datePickerDialog.show()
    }
}