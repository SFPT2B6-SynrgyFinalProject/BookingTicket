package id.synrgy6team2.bookingticket.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase
) : ViewModel() {
    private var _verify: LiveEvent<State<Unit>> = LiveEvent()

    val verify: LiveData<State<Unit>> = _verify

    fun verify(token: String?) {
        val dataToken = token ?: "-1"
        viewModelScope.launch {
            try {
                _verify.postValue(State.Loading())
                withContext(Dispatchers.IO) {
                    authenticationUseCase.executeVerify(dataToken.toInt())
                }
                _verify.postValue(State.Success(Unit))
            } catch (e: Exception) {
                _verify.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}