package id.synrgy6team2.bookingticket.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCase: OrderUseCase
) : ViewModel() {
    private var _getOrder: MutableStateFlow<State<List<GetOrderResponseModel.Data.OrdersItem>>> = MutableStateFlow(State.Success(emptyList()))

    val getOrder: StateFlow<State<List<GetOrderResponseModel.Data.OrdersItem>>> = _getOrder

    fun getOrder(status: String) {
        useCase.getOrder(status)
            .onStart { _getOrder.value = State.Loading() }
            .map { data -> State.Success(data.data?.orders ?: emptyList()) }
            .onEach { state -> _getOrder.value = state }
            .catch { e -> _getOrder.value = State.Error(null, e.message.toString()) }
            .launchIn(viewModelScope)
    }
}