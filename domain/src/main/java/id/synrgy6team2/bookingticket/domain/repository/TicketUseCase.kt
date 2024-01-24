package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel

/**
 * Application UseCase for Authentication feature that will connect to REMOTE & LOCAL
 * to interact directly to UI Layer / Presentation Layer
 * */
class TicketUseCase(private val ticketRepository: TicketRepository) {
    fun getFlightClass(): LiveData<FlightClassResponseModel> {
        return ticketRepository.getFlightClass()
    }

    fun getAirport(query: String?): LiveData<AirportResponseModel> {
        return ticketRepository.getAirport(query)
    }

    suspend fun getTicket(field: TicketRequestModel): TicketResponseModel {
        return ticketRepository.getTicket(field)
    }
}