package id.synrgy6team2.bookingticket.presentation.searchticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.domain.repository.TicketUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    private val ticketUseCase: TicketUseCase
) : ViewModel() {
    private var _airport: MutableLiveData<AirportResponseModel> = MutableLiveData()

    val airport: LiveData<AirportResponseModel> = _airport

    fun airport(query: String?) {
        viewModelScope.launch {
            ticketUseCase.getAirport(query).asFlow().collectLatest {
                _airport.postValue(it)
            }
        }
    }
}