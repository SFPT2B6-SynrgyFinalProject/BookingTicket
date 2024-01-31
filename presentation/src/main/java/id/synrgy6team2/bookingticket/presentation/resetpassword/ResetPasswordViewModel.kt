package id.synrgy6team2.bookingticket.presentation.resetpassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {

    private var _resetPassword: LiveEvent<State<ResetPasswordResponseModel>> = LiveEvent()

    val resetPassword: LiveData<State<ResetPasswordResponseModel>> = _resetPassword

    fun resetPassword(value: ResetPasswordRequestModel) {
        viewModelScope.launch {
            _resetPassword.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationUseCase.executeResetPassword(value)
                }
                _resetPassword.postValue(State.Success(response))
            } catch (e: Exception) {
                _resetPassword.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}