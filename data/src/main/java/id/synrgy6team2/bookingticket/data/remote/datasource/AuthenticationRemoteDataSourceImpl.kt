package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ForgotPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.LoginRequest
import id.synrgy6team2.bookingticket.data.remote.model.LoginResponse
import id.synrgy6team2.bookingticket.data.remote.model.RegisterRequest
import id.synrgy6team2.bookingticket.data.remote.model.RegisterResponse
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ResetPasswordResponse
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import retrofit2.Response

class AuthenticationRemoteDataSourceImpl(
    private val service: AuthenticationService
) : AuthenticationRemoteDataSource {
    data class ErrorMessage(
        val data: ResultItem? = null,
        val status: String? = null,
        val message: String? = null
    ) {
        data class ResultItem(
            val email: String? = null,
            val password: String? = null,
            val fullName: String? = null,
            val birthDate: String? = null,
            val gender: String? = null,
            val otp: String? = null,
            val newPassword: String? = null,
            val authentication: String? = null
        )
    }

    private fun specificErrorMessage(message: ErrorMessage): String {
        return when {
            !message.data?.email.isNullOrEmpty() -> message.data?.email
            !message.data?.password.isNullOrEmpty() -> message.data?.password
            !message.data?.fullName.isNullOrEmpty() -> message.data?.fullName
            !message.data?.birthDate.isNullOrEmpty() -> message.data?.birthDate
            !message.data?.gender.isNullOrEmpty() -> message.data?.gender
            !message.data?.newPassword.isNullOrEmpty() -> message.data?.newPassword
            else -> "Terjadi kesalahan"
        } ?: "Terjadi kesalahan pada server"
    }

    override suspend fun login(field: LoginRequest): LoginResponse? {
        val response = service.login(field)
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

    override suspend fun google(field: LoginRequest): LoginResponse? {
        val response = service.google(field)
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

    override suspend fun register(field: RegisterRequest): RegisterResponse? {
        val response = service.register(field)
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

    override suspend fun verify(token: Int): Unit? {
        val response = service.verify(token)
        return if (response.isSuccessful || response.code() == 303) {
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

    override suspend fun forgotPassword(field: ForgotPasswordRequest): ForgotPasswordResponse? {
        val response = service.forgotPassword(field)
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

    override suspend fun resetPassword(field: ResetPasswordRequest): ResetPasswordResponse? {
        val response = service.resetPassword(field)
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