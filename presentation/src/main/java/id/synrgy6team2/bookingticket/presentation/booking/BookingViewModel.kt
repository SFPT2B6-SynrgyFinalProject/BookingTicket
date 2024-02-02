package id.synrgy6team2.bookingticket.presentation.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.LiveEvent
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import id.synrgy6team2.bookingticket.domain.repository.OrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    account: AccountUseCase,
    private val orderUseCase: OrderUseCase
) : ViewModel() {
    private var _itemForms: MutableLiveData<List<String>> = MutableLiveData()
    private var _saveForms: MutableLiveData<List<String>> = MutableLiveData()
    private var _createOrder: LiveEvent<State<CreateOrderResponseModel>> = LiveEvent()

    val showProfile: LiveData<ProfileResponseModel> = account.executeProfile()
    val itemForms: LiveData<List<String>> = _itemForms
    val saveForms: LiveData<List<String>> = _saveForms
    val createOrder: LiveData<State<CreateOrderResponseModel>> = _createOrder

    fun itemForms(count: Int) {
        val items = mutableListOf<String>()
        for (i in 1..<count) {
            items.add("")
        }
        _itemForms.value = items
        _saveForms.value = items
    }

    fun saveForms(position: Int, value: String) {
        val currentValue = _saveForms.value.orEmpty().toMutableList()
        currentValue[position] = value
        _saveForms.value = currentValue
    }

    fun createOrder(value: CreateOrderRequestModel) {
        viewModelScope.launch {
            _createOrder.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    orderUseCase.createOrder(value)
                }
                withContext(Dispatchers.Main) {
                    _createOrder.postValue(State.Success(response))
                }
            } catch (e: Exception) {
                _createOrder.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}