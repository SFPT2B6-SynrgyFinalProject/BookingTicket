package id.synrgy6team2.bookingticket.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import id.synrgy6team2.bookingticket.domain.repository.NotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val useCase: NotificationUseCase
) : ViewModel() {
    private var _getNotification: MutableStateFlow<State<List<NotificationResponseModel.Data.NotificationItem>>> = MutableStateFlow(State.Success(emptyList()))

    val getNotification: StateFlow<State<List<NotificationResponseModel.Data.NotificationItem>>> = _getNotification

    fun getNotification() {
        useCase.getNotif()
            .onStart { _getNotification.value = State.Loading() }
            .map { data -> State.Success(data.data?.notification ?: emptyList()) }
            .onEach { state -> _getNotification.value = state }
            .catch { e -> _getNotification.value = State.Error(null, e.message.toString()) }
            .launchIn(viewModelScope)
    }
}