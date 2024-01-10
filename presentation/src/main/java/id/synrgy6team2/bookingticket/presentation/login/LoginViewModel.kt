package id.synrgy6team2.bookingticket.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    private var _login: LiveEvent<State<LoginResponseModel>> = LiveEvent()
    private var _logged: LiveEvent<State<Boolean>> = LiveEvent()

    val login: LiveData<State<LoginResponseModel>> = _login
    val logged: LiveData<State<Boolean>> = _logged

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

    fun logged() {
        viewModelScope.launch {
            val response = authenticationUseCase.executeCheckLogged()
            _logged.postValue(State.Success(response))
        }
    }
}