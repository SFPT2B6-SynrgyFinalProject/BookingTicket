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
    /**
     * FLIGHT CLASS Feature
     *
     * @return [FlightClassResponseModel] - Data Class (FlightClassResponseModel)
     * */
    fun getFlightClass(): LiveData<FlightClassResponseModel> {
        return ticketRepository.getFlightClass()
    }

    /**
     * AIRPORT Feature
     *
     * @param query [String] - Data Class (String)
     *
     * @return [AirportResponseModel] - Data Class (AirportResponseModel)
     * */
    fun getAirport(query: String?): LiveData<AirportResponseModel> {
        return ticketRepository.getAirport(query)
    }

    /**
     * TICKET Features
     *
     * @param field [TicketRequestModel] - Data Class (TicketRequestModel)
     *
     * @return [TicketResponseModel] - Data Class (TicketResponseModel)
     *
     * @exception message [String] - Handle Error
     * */
    suspend fun getTicket(field: TicketRequestModel): TicketResponseModel {
        return ticketRepository.getTicket(field)
    }
}