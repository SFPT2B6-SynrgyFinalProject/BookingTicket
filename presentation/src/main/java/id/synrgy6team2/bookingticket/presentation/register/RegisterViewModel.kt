package id.synrgy6team2.bookingticket.presentation.register

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private var _login: LiveEvent<State<LoginResponseModel>> = LiveEvent()
    private var _register: LiveEvent<State<RegisterResponseModel>> = LiveEvent()

    val login: LiveData<State<LoginResponseModel>> = _login
    val register: LiveData<State<RegisterResponseModel>> = _register
    val saveStateBirthDate: MutableLiveData<String?> = MutableLiveData()

    fun register(value: RegisterRequestModel) {
        viewModelScope.launch {
            _register.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationUseCase.executeRegister(value)
                }
                _register.postValue(State.Success(response))
            } catch (e: Exception) {
                _register.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    private fun google(value: LoginRequestModel) {
        viewModelScope.launch {
            _login.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationUseCase.executeGoogle(value)
                }
                _login.postValue(State.Success(response))
            } catch (e: Exception) {
                _login.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    fun googleSignInFromIntent(intent: Intent) {
        viewModelScope.launch {
            val account = try {
                withContext(Dispatchers.IO) {
                    GoogleSignIn.getSignedInAccountFromIntent(intent).await()
                }
            } catch (e: ApiException) {
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
            account?.let {
                val value = LoginRequestModel(googleToken = it.idToken)
                google(value)
            }
        }
    }
}