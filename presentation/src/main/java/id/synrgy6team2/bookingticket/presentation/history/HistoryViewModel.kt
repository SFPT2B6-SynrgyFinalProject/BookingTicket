package id.synrgy6team2.bookingticket.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCase: OrderUseCase
) : ViewModel() {
    private var _getOrder: MutableLiveData<State<GetOrderResponseModel>> = MutableLiveData()

    val getOrder: LiveData<State<GetOrderResponseModel>> = _getOrder

    fun getOrder(status: String) {
        viewModelScope.launch {
            _getOrder.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    useCase.getOrder(status)
                }
                withContext(Dispatchers.Main) {
                    _getOrder.postValue(State.Success(response))
                }
            } catch (e: Exception) {
                _getOrder.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}