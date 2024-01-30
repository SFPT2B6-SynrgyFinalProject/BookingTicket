package id.synrgy6team2.bookingticket.presentation.ticket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel
import id.synrgy6team2.bookingticket.domain.repository.TicketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PemilihanTiketViewModel @Inject constructor(
    private val useCase: TicketUseCase
) : ViewModel() {
    private var _getTicket: MutableLiveData<State<TicketResponseModel>> = MutableLiveData()

    val getTicket: LiveData<State<TicketResponseModel>> = _getTicket

    fun getTicket(value: TicketRequestModel) {
        viewModelScope.launch {
            _getTicket.postValue(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    useCase.getTicket(value)
                }
                _getTicket.postValue(State.Success(response))
            } catch (e: Exception) {
                _getTicket.postValue(State.Error(null, e.message.toString()))
            }
        }
    }
}