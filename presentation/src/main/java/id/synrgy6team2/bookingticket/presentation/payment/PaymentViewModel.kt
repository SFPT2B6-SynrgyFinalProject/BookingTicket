package id.synrgy6team2.bookingticket.presentation.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val useCase: OrderUseCase
): ViewModel() {
    private var _orderDetail: LiveEvent<State<GetOrderDetailResponseModel>> = LiveEvent()
    private var _paymentOrder: LiveEvent<State<PayOrderResponseModel>> = LiveEvent()

    val orderDetail: LiveData<State<GetOrderDetailResponseModel>> = _orderDetail
    val paymentOrder: LiveData<State<PayOrderResponseModel>> = _paymentOrder

    fun orderDetail(orderId: String) {
        viewModelScope.launch {
            _orderDetail.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    useCase.getOrderDetail(orderId)
                }
                withContext(Dispatchers.Main) {
                    _orderDetail.postValue(State.Success(response))
                }
            } catch (e: Exception) {
                _orderDetail.postValue(State.Error(null, e.message.toString()))
            }
        }
    }

    fun paymentOrder(value: PayOrderRequestModel) {
        viewModelScope.launch {
            _paymentOrder.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    useCase.payOrder(value)
                }
                withContext(Dispatchers.Main) {
                    _paymentOrder.postValue(State.Success(response))
                }
            } catch (e: Exception) {
                _paymentOrder.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}