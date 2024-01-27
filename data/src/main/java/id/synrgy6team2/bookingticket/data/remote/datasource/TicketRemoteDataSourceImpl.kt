package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.gson.Gson
import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.data.remote.model.TicketRequest
import id.synrgy6team2.bookingticket.data.remote.model.TicketResponse
import id.synrgy6team2.bookingticket.data.remote.service.TicketService

class TicketRemoteDataSourceImpl(
    private val service: TicketService
) : TicketRemoteDataSource {
    data class ErrorMessage(
        val data: ResultItem? = null,
        val status: String? = null,
        val message: String? = null
    ) {
        data class ResultItem(
            val classId: String? = null,
            val dataPerPage: String? = null,
            val passenger: Passenger? = null,
            val departureCode: String? = null,
            val sortBy: String? = null,
            val page: String? = null,
            val departureDateStart: String? = null,
            val arrivalCode: String? = null,
            val departureDateEnd: String? = null,
            val airlineId: String? = null,
            val authentication: String? = null
        ) {
            data class Passenger(
                val adult: String? = null,
                val infant: String? = null,
                val child: String? = null
            )
        }
    }

    private fun specificErrorMessage(message: ErrorMessage): String {
        return when {
            !message.data?.classId.isNullOrEmpty() -> message.data?.classId
            !message.data?.dataPerPage.isNullOrEmpty() -> message.data?.dataPerPage
            !message.data?.passenger?.adult.isNullOrEmpty() -> message.data?.passenger?.adult
            !message.data?.passenger?.infant.isNullOrEmpty() -> message.data?.passenger?.infant
            !message.data?.passenger?.child.isNullOrEmpty() -> message.data?.passenger?.child
            !message.data?.departureCode.isNullOrEmpty() -> message.data?.departureCode
            !message.data?.sortBy.isNullOrEmpty() -> message.data?.sortBy
            !message.data?.departureDateStart.isNullOrEmpty() -> message.data?.departureDateStart
            !message.data?.arrivalCode.isNullOrEmpty() -> message.data?.arrivalCode
            !message.data?.departureDateEnd.isNullOrEmpty() -> message.data?.departureDateEnd
            !message.data?.airlineId.isNullOrEmpty() -> message.data?.airlineId
            !message.data?.authentication.isNullOrEmpty() -> message.data?.authentication
            else -> "Terjadi kesalahan"
        } ?: "Terjadi kesalahan pada server"
    }

    override suspend fun getFlightClass(): FlightClassResponse? {
        val response = service.flightType()
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }

    override suspend fun getAirport(query: String?): AirportResponse? {
        val response = service.airport(query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }

    override suspend fun getTicket(field: TicketRequest): TicketResponse? {
        val response = service.ticket(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }
}