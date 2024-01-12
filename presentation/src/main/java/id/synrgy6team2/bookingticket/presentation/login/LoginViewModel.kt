package id.synrgy6team2.bookingticket.presentation.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    private var _login: LiveEvent<State<LoginResponseModel>> = LiveEvent()
    private var _logged: LiveEvent<State<Unit>> = LiveEvent()
    private val _googleSignInFromIntent: LiveEvent<State<GoogleSignInAccount>> = LiveEvent()

    val login: LiveData<State<LoginResponseModel>> = _login
    val logged: LiveData<State<Unit>> = _logged
    val googleSignInFromIntent: LiveEvent<State<GoogleSignInAccount>> = _googleSignInFromIntent

    fun login(value: LoginRequestModel) {
        viewModelScope.launch {
            _login.postValue(State.Loading())
            try {
                val response = authenticationUseCase.executeLogin(value)
                _login.postValue(State.Success(response))
            } catch (e: Exception) {
                _login.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    fun google(value: LoginRequestModel) {
        viewModelScope.launch {
            _login.postValue(State.Loading())
            try {
                val response = authenticationUseCase.executeGoogle(value)
                _login.postValue(State.Success(response))
            } catch (e: Exception) {
                _login.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    fun logged() {
        viewModelScope.launch {
            val response = authenticationUseCase.executeCheckLogged()
            if (response) {
                _logged.postValue(State.Success(Unit))
            } else {
                _logged.postValue(State.Error(null, ""))
            }
        }
    }

    fun googleSignInFromIntent(intent: Intent) {
        viewModelScope.launch {
            _googleSignInFromIntent.postValue(State.Loading())
            try {
                val account = withContext(Dispatchers.IO) {
                    GoogleSignIn.getSignedInAccountFromIntent(intent).await()
                }
                _googleSignInFromIntent.postValue(State.Success(account))
            } catch (e: ApiException) {
                FirebaseCrashlytics.getInstance().recordException(e)
            }
        }
    }
}