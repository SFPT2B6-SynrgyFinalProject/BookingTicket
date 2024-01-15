package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.LoginRequest
import id.synrgy6team2.bookingticket.data.remote.model.LoginResponse
import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordResponse

interface AuthenticationDataSource {
    suspend fun login(field: LoginRequest): LoginResponse?
    suspend fun google(field: LoginRequest): LoginResponse?
    suspend fun register(field: RegisterRequest): RegisterResponse?
    suspend fun verify(token: Int): Unit?
    suspend fun forgotPassword(field: ForgotPasswordRequest): ForgotPasswordResponse?
    suspend fun resetPassword(field: ResetPasswordRequest): ResetPasswordResponse?
}