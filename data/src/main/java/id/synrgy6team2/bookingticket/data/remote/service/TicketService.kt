package id.synrgy6team2.bookingticket.data.remote.service

import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.data.remote.model.TicketRequest
import id.synrgy6team2.bookingticket.data.remote.model.TicketResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TicketService {
    @GET("flight/class")
    suspend fun flightType(): Response<FlightClassResponse>

    @GET("flight/airport")
    suspend fun airport(
        @Query("value") query: String? = ""
    ): Response<AirportResponse>

    @POST("flight/ticket")
    suspend fun ticket(
        @Body field: TicketRequest
    ): Response<TicketResponse>
}