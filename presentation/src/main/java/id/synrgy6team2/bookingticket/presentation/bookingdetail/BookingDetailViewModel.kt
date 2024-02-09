package id.synrgy6team2.bookingticket.presentation.bookingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingDetailViewModel @Inject constructor(
    private val useCase: OrderUseCase
): ViewModel() {
    private var _orderDetail: LiveEvent<State<GetOrderDetailResponseModel>> = LiveEvent()
    val orderDetail: LiveData<State<GetOrderDetailResponseModel>> = _orderDetail
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

    fun passengerDetail(
        name: String?,
        passenger: GetOrderDetailResponseModel.Data.PassengerDetails?
    ): List<Passenger> {
        val list = mutableListOf<Passenger>()
        list.add(Passenger(name, "Dewasa"))
        passenger?.adult?.let {
            list.addAll(it.map { name -> Passenger(name, "Dewasa") })
        }
        passenger?.child?.let {
            list.addAll(it.map { name -> Passenger(name, "Anak Kecil") })
        }
        passenger?.infant?.let {
            list.addAll(it.map { name -> Passenger(name, "Bayi") })
        }
        return list.distinct()
    }

    data class Passenger(val name: String?, val type: String)
}