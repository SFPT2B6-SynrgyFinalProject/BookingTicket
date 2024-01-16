package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.LoginRequest
import id.synrgy6team2.bookingticket.data.remote.model.LoginResponse
import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService

class AuthenticationDataSourceImpl(
    private val authentication: AuthenticationService
) : AuthenticationDataSource {
    private fun exception(code: Int): Exception {
        return when (code) {
            400 -> Exception("$code - Bad Request!")
            401 -> Exception("$code - Unauthorized!")
            404 -> Exception("$code - Not Found!")
            else -> Exception("$code - Error Occurred!")
        }
    }

    override suspend fun login(field: LoginRequest): LoginResponse? {
        val response = authentication.login(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun google(field: LoginRequest): LoginResponse? {
        val response = authentication.google(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun register(field: RegisterRequest): RegisterResponse? {
        val response = authentication.register(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun verify(token: Int): Unit? {
        val response = authentication.verify(token)
        return if (response.isSuccessful || response.code() == 303) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun forgotPassword(field: ForgotPasswordRequest): ForgotPasswordResponse? {
        val response = authentication.forgotPassword(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }

    override suspend fun resetPassword(field: ResetPasswordRequest): ResetPasswordResponse? {
        val response = authentication.resetPassword(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw exception(code)
        }
    }
}