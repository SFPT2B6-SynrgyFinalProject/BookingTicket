package id.synrgy6team2.bookingticket.data.remote.service

import id.synrgy6team2.bookingticket.data.remote.model.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface NotificationService {
    @GET("notifications")
    suspend fun getNotif(
        @Header("Authorization") token: String
    ): Response<NotificationResponse>
}