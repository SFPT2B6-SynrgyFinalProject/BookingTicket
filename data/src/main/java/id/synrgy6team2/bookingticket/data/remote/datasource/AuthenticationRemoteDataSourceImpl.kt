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

class AuthenticationRemoteDataSourceImpl(
    private val service: AuthenticationService
) : AuthenticationRemoteDataSource {
    override suspend fun login(field: LoginRequest): LoginResponse? {
        val response = service.login(field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            throw when (code) {
                400 -> Exception("$code - Email anda tidak terdaftar/terverify!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Permintaan buruk!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Email anda telah terdaftar/verify!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Token tidak sah!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Email anda belum terdaftar/terverify!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
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
            throw when (code) {
                400 -> Exception("$code - Token tidak sah!")
                401 -> Exception("$code - Tidak sah!")
                404 -> Exception("$code - Tidak ditemukan!")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }
}