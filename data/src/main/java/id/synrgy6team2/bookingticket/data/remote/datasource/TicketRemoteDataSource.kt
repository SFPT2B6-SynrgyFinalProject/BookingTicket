package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.data.remote.model.TicketRequest
import id.synrgy6team2.bookingticket.data.remote.model.TicketResponse

interface TicketRemoteDataSource {
    suspend fun getFlightClass(): FlightClassResponse?

    suspend fun getAirport(query: String?): AirportResponse?

    suspend fun getTicket(field: TicketRequest): TicketResponse?
}