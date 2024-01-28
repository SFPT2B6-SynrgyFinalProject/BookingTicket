package id.synrgy6team2.bookingticket.presentation.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    private var _login: LiveEvent<State<LoginResponseModel>> = LiveEvent()

    val login: LiveData<State<LoginResponseModel>> = _login

    fun login(value: LoginRequestModel) {
        viewModelScope.launch {
            try {
                _login.postValue(State.Loading())
                val response = withContext(Dispatchers.IO) {
                    authenticationUseCase.executeLogin(value)
                }
                _login.postValue(State.Success(response))
            } catch (e: Exception) {
                _login.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    private fun google(value: LoginRequestModel) {
        viewModelScope.launch {
            try {
                _login.postValue(State.Loading())
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