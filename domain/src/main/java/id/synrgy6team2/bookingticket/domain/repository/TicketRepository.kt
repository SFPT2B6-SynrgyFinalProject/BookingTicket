package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel

interface TicketRepository {
    fun getFlightClass(): LiveData<FlightClassResponseModel>

    fun getAirport(query: String?): LiveData<AirportResponseModel>

    suspend fun getTicket(field: TicketRequestModel): TicketResponseModel
}