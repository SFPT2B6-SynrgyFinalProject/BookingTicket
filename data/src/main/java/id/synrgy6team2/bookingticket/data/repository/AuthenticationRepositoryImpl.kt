package id.synrgy6team2.bookingticket.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationRemoteDataSource
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : AuthenticationRepository {
    override suspend fun login(field: LoginRequestModel): LoginResponseModel {
        return try {
            val response = authenticationRemoteDataSource.login(field.toData())
            withContext(Dispatchers.Main) {
                preferenceDataSource.setLogin(true)
                preferenceDataSource.setToken(response?.data?.token ?: "")
                response?.toDomain() ?: LoginResponseModel()
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun google(field: LoginRequestModel): LoginResponseModel {
        return try {
            val response = authenticationRemoteDataSource.google(field.toData())
            withContext(Dispatchers.Main) {
                preferenceDataSource.setLogin(true)
                preferenceDataSource.setToken(response?.data?.token ?: "")
                response?.toDomain() ?: LoginResponseModel()
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun register(field: RegisterRequestModel): RegisterResponseModel {
        return try {
            val response = authenticationRemoteDataSource.register(field.toData())
            response?.toDomain() ?: RegisterResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun verify(token: Int?) {
        return try {
            val response = authenticationRemoteDataSource.verify(token ?: -1)
            val countVerify = preferenceDataSource.getCountVerify().first()
            if (countVerify >= 1) {
                throw Exception("400 - The token has been used!")
            } else {
                preferenceDataSource.setCountVerify(2)
                response ?: Unit
            }
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun forgotPassword(field: ForgotPasswordRequestModel): ForgotPasswordResponseModel {
        return try {
            val response = authenticationRemoteDataSource.forgotPassword(field.toData())
            response?.toDomain() ?: ForgotPasswordResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun resetPassword(field: ResetPasswordRequestModel): ResetPasswordResponseModel {
        return try {
            val response = authenticationRemoteDataSource.resetPassword(field.toData())
            response?.toDomain() ?: ResetPasswordResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override fun logout() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                accountLocalDataSource.logout()
                preferenceDataSource.setLogin(false)
                preferenceDataSource.setToken("")
                preferenceDataSource.setCountVerify(-1)
            }
        }
    }

    override fun checkLogged(): LiveData<Boolean> {
        return preferenceDataSource.getLogin().combine(preferenceDataSource.getToken()) { login: Boolean, token: String ->
            login && token.isNotEmpty()
        }.asLiveData()
    }
}