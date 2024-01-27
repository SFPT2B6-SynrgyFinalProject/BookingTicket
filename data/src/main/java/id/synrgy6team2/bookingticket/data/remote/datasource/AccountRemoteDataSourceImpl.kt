package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.ProfileResponse
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserRequest
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserResponse
import id.synrgy6team2.bookingticket.data.remote.service.AccountService

class AccountRemoteDataSourceImpl(
    private val service: AccountService
) : AccountRemoteDataSource {
    data class ErrorMessage(
        val data: ResultItem? = null,
        val status: String? = null,
        val message: String? = null
    ) {
        data class ResultItem(
            val email: String? = null,
            val noHp: String? = null,
            val fullName: String? = null,
            val birthDate: String? = null,
            val gender: String? = null,
            val currentPassword: String? = null,
            val newPassword: String? = null,
            val confirmPassword: String? = null,
            val authentication: String? = null
        )
    }

    private fun specificErrorMessage(message: ErrorMessage): String {
        return when {
            !message.data?.email.isNullOrEmpty() -> message.data?.email
            !message.data?.noHp.isNullOrEmpty() -> message.data?.noHp
            !message.data?.fullName.isNullOrEmpty() -> message.data?.fullName
            !message.data?.birthDate.isNullOrEmpty() -> message.data?.birthDate
            !message.data?.gender.isNullOrEmpty() -> message.data?.gender
            !message.data?.currentPassword.isNullOrEmpty() -> message.data?.currentPassword
            !message.data?.newPassword.isNullOrEmpty() -> message.data?.newPassword
            !message.data?.confirmPassword.isNullOrEmpty() -> message.data?.confirmPassword
            !message.data?.authentication.isNullOrEmpty() -> message.data?.authentication
            else -> "Terjadi kesalahan"
        } ?: "Terjadi kesalahan pada server"
    }

    override suspend fun profile(token: String): ProfileResponse? {
        val response = service.profile("Bearer $token")
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

    override suspend fun updateUser(token: String, field: UpdateUserRequest): UpdateUserResponse? {
        val response = service.updateProfile("Bearer $token", field)
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

    override suspend fun changePassword(
        token: String,
        field: ChangePasswordRequest
    ): ChangePasswordResponse? {
        val response = service.changePassword("Bearer $token", field)
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