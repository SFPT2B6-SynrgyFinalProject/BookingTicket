package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.LoginRequest
import id.synrgy6team2.bookingticket.data.remote.model.LoginResponse
import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AuthenticationDataSourceImpl(
    private val authentication: AuthenticationService
) : AuthenticationDataSource {
    private fun exception(code: Int): Exception {
        return when (code) {
            400 -> Exception("$code - Bad Request!")
            401 -> Exception("$code - Unauthorized!")
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

    override suspend fun register(field: RegisterRequest): RegisterResponse? {
        val response = authentication.register(field)
        return if (response.isSuccessful) {
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