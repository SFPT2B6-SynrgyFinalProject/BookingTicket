package id.synrgy6team2.bookingticket.presentation.forgotpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LupaPasswordViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    private var _forgetPassword: LiveEvent<State<ForgotPasswordResponseModel>> = LiveEvent()

    val forgetPassword: LiveData<State<ForgotPasswordResponseModel>> = _forgetPassword

    fun forgetPassword(value: ForgotPasswordRequestModel) {
        viewModelScope.launch {
            _forgetPassword.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationUseCase.executeForgotPassword(value)
                }
                _forgetPassword.postValue(State.Success(response))
            } catch (e: Exception) {
                _forgetPassword.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}