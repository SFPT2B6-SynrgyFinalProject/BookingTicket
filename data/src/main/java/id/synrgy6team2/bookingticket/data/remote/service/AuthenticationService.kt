package id.synrgy6team2.bookingticket.data.remote.service

import retrofit2.http.GET
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    suspend fun login(

    )
}