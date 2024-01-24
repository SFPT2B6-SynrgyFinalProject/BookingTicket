package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.data.remote.model.TicketRequest
import id.synrgy6team2.bookingticket.data.remote.model.TicketResponse
import id.synrgy6team2.bookingticket.data.remote.service.TicketService

class TicketRemoteDataSourceImpl(
    private val service: TicketService
) : TicketRemoteDataSource {
    private fun exception(code: Int): Exception {
        return when (code) {
            400 -> Exception("$code - Bad Request!")
            401 -> Exception("$code - Unauthorized!")
            404 -> Exception("$code - Not Found!")
            else -> Exception("$code - Error Occurred!")
        }
    }

    override suspend fun getFlightClass(): FlightClassResponse? {
        val response = service.flightType()
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun getAirport(query: String?): AirportResponse? {
        val response = service.airport(query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun getTicket(field: TicketRequest): TicketResponse? {
        val response = service.ticket(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }
}