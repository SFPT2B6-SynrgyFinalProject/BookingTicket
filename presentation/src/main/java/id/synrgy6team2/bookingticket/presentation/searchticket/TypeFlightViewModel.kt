package id.synrgy6team2.bookingticket.presentation.searchticket

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel
import id.synrgy6team2.bookingticket.domain.repository.TicketUseCase
import javax.inject.Inject

@HiltViewModel
class TypeFlightViewModel @Inject constructor(
    ticketUseCase: TicketUseCase
) : ViewModel() {

    val flightClass: LiveData<FlightClassResponseModel> = ticketUseCase.getFlightClass()

}