package id.synrgy6team2.bookingticket.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.synrgy6team2.bookingticket.common.State
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationDataSource
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ForgotPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.LoginRequestModel
import id.synrgy6team2.bookingticket.domain.model.LoginResponseModel
import id.synrgy6team2.bookingticket.domain.model.RegisterRequestModel
import id.synrgy6team2.bookingticket.domain.model.RegisterResponseModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ResetPasswordResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepositoryImpl(
    private val authenticationRemote: AuthenticationDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : AuthenticationRepository {
    override suspend fun login(field: LoginRequestModel): LiveData<State<LoginResponseModel>> {
        return liveData(Dispatchers.IO) {
            emit(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationRemote.login(field.toData())
                }
                withContext(Dispatchers.Main) {
                    preferenceDataSource.setLogin(true)
                    preferenceDataSource.setToken(response?.data?.token ?: "")
                    emit(State.Success(response?.toDomain() ?: LoginResponseModel()))
                }
            } catch (e: Exception) {
                emit(State.Error(null, e.message.toString()))
            }
        }
    }

    override suspend fun register(field: RegisterRequestModel): LiveData<State<RegisterResponseModel>> {
        return liveData(Dispatchers.IO) {
            emit(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationRemote.register(field.toData())
                }
                emit(State.Success(response?.toDomain() ?: RegisterResponseModel()))
            } catch (e: Exception) {
                emit(State.Error(null, e.message.toString()))
            }
        }
    }

    override suspend fun forgotPassword(field: ForgotPasswordRequestModel): LiveData<State<ForgotPasswordResponseModel>> {
        return liveData(Dispatchers.IO) {
            emit(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationRemote.forgotPassword(field.toData())
                }
                emit(State.Success(response?.toDomain() ?: ForgotPasswordResponseModel()))
            } catch (e: Exception) {
                emit(State.Error(null, e.message.toString()))
            }
        }
    }

    override suspend fun resetPassword(field: ResetPasswordRequestModel): LiveData<State<ResetPasswordResponseModel>> {
        return liveData(Dispatchers.IO) {
            emit(State.Loading())
            try {
                val response = withContext(Dispatchers.IO) {
                    authenticationRemote.resetPassword(field.toData())
                }
                emit(State.Success(response?.toDomain() ?: ResetPasswordResponseModel()))
            } catch (e: Exception) {
                emit(State.Error(null, e.message.toString()))
            }
        }
    }
}