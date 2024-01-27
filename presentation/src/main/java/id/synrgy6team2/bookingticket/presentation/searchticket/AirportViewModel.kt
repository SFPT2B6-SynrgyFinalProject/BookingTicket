package id.synrgy6team2.bookingticket.presentation.searchticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.domain.repository.TicketUseCase
import javax.inject.Inject

@HiltViewModel
class AirportViewModel @Inject constructor(
    ticketUseCase: TicketUseCase
) : ViewModel() {

    val airport: LiveData<AirportResponseModel> = ticketUseCase.getAirport("")

    val selectedAirport: MutableLiveData<AirportResponseModel.DataItem> = MutableLiveData()
    val selectedAirportToDestination: MutableLiveData<AirportResponseModel.DataItem> =
        MutableLiveData()

}