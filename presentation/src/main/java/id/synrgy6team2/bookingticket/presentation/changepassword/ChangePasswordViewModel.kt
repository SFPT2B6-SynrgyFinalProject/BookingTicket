package id.synrgy6team2.bookingticket.presentation.changepassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val useCase: AccountUseCase
) : ViewModel() {
    private var _changePassword: MutableLiveData<State<ChangePasswordResponseModel>> = MutableLiveData()

    val changePassword: LiveData<State<ChangePasswordResponseModel>> = _changePassword

    fun changePassword(value: ChangePasswordRequestModel) {
        viewModelScope.launch {
            try {
                _changePassword.postValue(State.Loading())
                val response = withContext(Dispatchers.IO) {
                    useCase.executeChangePassword(value)
                }
                _changePassword.postValue(State.Success(response))
            } catch (e: Exception) {
                _changePassword.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}