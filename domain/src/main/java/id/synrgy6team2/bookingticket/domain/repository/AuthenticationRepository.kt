package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel
import java.util.concurrent.Flow

interface AuthenticationRepository {
    suspend fun login(field: LoginRequestModel): LoginResponseModel
    suspend fun google(field: LoginRequestModel): LoginResponseModel
    suspend fun register(field: RegisterRequestModel): RegisterResponseModel
    suspend fun verify(token: Int?)
    suspend fun forgotPassword(field: ForgotPasswordRequestModel): ForgotPasswordResponseModel
    suspend fun resetPassword(field: ResetPasswordRequestModel): ResetPasswordResponseModel
    suspend fun logout()
    fun checkLogged(): LiveData<Boolean>
}
