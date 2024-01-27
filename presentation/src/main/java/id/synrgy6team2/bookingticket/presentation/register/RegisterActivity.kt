package id.synrgy6team2.bookingticket.presentation.register

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.synrgy6team2.bookingticket.common.R
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.common.StyleType
import id.synrgy6team2.bookingticket.common.ValidationType
import id.synrgy6team2.bookingticket.common.onToast
import id.synrgy6team2.bookingticket.common.onValidation
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.presentation.databinding.ActivityRegisterBinding
import id.synrgy6team2.bookingticket.presentation.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.let { intent ->
            viewModel.googleSignInFromIntent(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindObserver()
        bindView()
    }

    private fun bindObserver() {
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
                    val response = state.data?.data?.email
                    val intent = Intent()
                    intent.putExtra("SUCCESS", response)
                    setResult(Activity.RESULT_OK, intent)
                    finish();
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
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = selection

            val tvDate = SimpleDateFormat("dd-MM-yyyy").format(calendar.time)
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(calendar.time)

            binding.tieDateOfBirth.setText(tvDate)
            viewModel.saveStateBirthDate.value = date
        }
        picker.show(supportFragmentManager, picker.toString())
    }
}
