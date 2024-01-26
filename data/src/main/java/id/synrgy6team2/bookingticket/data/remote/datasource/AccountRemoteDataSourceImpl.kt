package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordRequest
import id.synrgy6team2.bookingticket.data.remote.model.ChangePasswordResponse
import id.synrgy6team2.bookingticket.data.remote.model.ProfileResponse
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserRequest
import id.synrgy6team2.bookingticket.data.remote.model.UpdateUserResponse
import id.synrgy6team2.bookingticket.data.remote.service.AccountService

class AccountRemoteDataSourceImpl(
    private val service: AccountService
) : AccountRemoteDataSource {
    override suspend fun profile(token: String): ProfileResponse? {
        val response = service.profile("Bearer $token")
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw when (code) {
                400 -> Exception("$code - Permintaan buruk!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Email tidak sah!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Password lama tidak sah!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }
}