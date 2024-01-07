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
    private val httpException = Exception("Tidak dapat berkomunikasi dengan server!")
    private val exception = Exception("Terjadi kesalahan!")

    override suspend fun login(field: LoginRequest): LoginResponse? {
        return try {
            withContext(Dispatchers.IO) {
                authentication.login(field).body()
            }
        } catch (e: HttpException) {
            Firebase.crashlytics.log(e.response()?.errorBody()?.string().toString())
            throw httpException
        } catch (e: Exception) {
            Firebase.crashlytics.log(e.message.toString())
            throw exception
        }
    }

    override suspend fun register(field: RegisterRequest): RegisterResponse? {
        return try {
            authentication.register(field).body()
        } catch (e: HttpException) {
            Firebase.crashlytics.log(e.response()?.errorBody()?.string().toString())
            throw httpException
        } catch (e: Exception) {
            Firebase.crashlytics.log(e.message.toString())
            throw exception
        }
    }

    override suspend fun forgotPassword(field: ForgotPasswordRequest): ForgotPasswordResponse? {
        return try {
            authentication.forgotPassword(field).body()
        } catch (e: HttpException) {
            Firebase.crashlytics.log(e.response()?.errorBody()?.string().toString())
            throw httpException
        } catch (e: Exception) {
            Firebase.crashlytics.log(e.message.toString())
            throw exception
        }
    }

    override suspend fun resetPassword(field: ResetPasswordRequest): ResetPasswordResponse? {
        return try {
            authentication.resetPassword(field).body()
        } catch (e: HttpException) {
            Firebase.crashlytics.log(e.response()?.errorBody()?.string().toString())
            throw httpException
        } catch (e: Exception) {
            Firebase.crashlytics.log(e.message.toString())
            throw exception
        }
    }
}