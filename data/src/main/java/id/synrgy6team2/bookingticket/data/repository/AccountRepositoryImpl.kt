package id.synrgy6team2.bookingticket.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.networkBoundResource
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.mapper.toLocal
import id.synrgy6team2.bookingticket.data.mapper.toProfileDomain
import id.synrgy6team2.bookingticket.data.mapper.toUpdateProfileDomain
import id.synrgy6team2.bookingticket.data.remote.datasource.AccountRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.model.ProfileResponse
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordRequestModel
import id.synrgy6team2.bookingticket.domain.model.ChangePasswordResponseModel
import id.synrgy6team2.bookingticket.domain.model.ProfileResponseModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserRequestModel
import id.synrgy6team2.bookingticket.domain.model.UpdateUserResponseModel
import id.synrgy6team2.bookingticket.domain.repository.AccountRepository
import kotlinx.coroutines.flow.first

class AccountRepositoryImpl(
    private val accountRemoteDataSource: AccountRemoteDataSource,
    private val accountLocalDataSource: AccountLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val room: RoomDB
) : AccountRepository {
    override fun profile(): LiveData<ProfileResponseModel> {
        return networkBoundResource(
            query = {
                val token = preferenceDataSource.getToken().first()
                accountLocalDataSource.getAccount(token)
            },
            fetch = {
                val token = preferenceDataSource.getToken().first()
                accountRemoteDataSource.profile(token)
            },
            saveFetchResult = { response: ProfileResponse? ->
                val token = preferenceDataSource.getToken().first()
                room.withTransaction {
                    accountLocalDataSource.removeAccount()
                    accountLocalDataSource.setAccount(
                        response?.data?.toLocal(token) ?: ProfileEntity(token = "")
                    )
                }
            }
        ).asLiveData().map { state: StateLocal<ProfileEntity> ->
            ProfileResponseModel(data = state.data?.toProfileDomain())
        }
    }

    override fun updateProfile(field: UpdateUserRequestModel): LiveData<StateLocal<UpdateUserResponseModel>> {
        return networkBoundResource(
            query = {
                val token = preferenceDataSource.getToken().first()
                accountLocalDataSource.getAccount(token)
            },
            fetch = {
                val token = preferenceDataSource.getToken().first()
                accountRemoteDataSource.profile(token)
            },
            saveFetchResult = { response: ProfileResponse? ->
                val token = preferenceDataSource.getToken().first()
                room.withTransaction {
                    accountLocalDataSource.removeAccount()
                    accountLocalDataSource.setAccount(
                        response?.data?.toLocal(token) ?: ProfileEntity(token = "")
                    )
                }
            }
        ).asLiveData().map { state: StateLocal<ProfileEntity> ->
            when (state) {
                is StateLocal.Loading -> StateLocal.Loading(UpdateUserResponseModel(data = state.data?.toUpdateProfileDomain()))
                is StateLocal.Success -> StateLocal.Success(UpdateUserResponseModel(data = state.data?.toUpdateProfileDomain()))
                is StateLocal.Error -> StateLocal.Error(state.error ?: Throwable(""), UpdateUserResponseModel(data = state.data?.toUpdateProfileDomain()))
            }
        }
    }

    override suspend fun changePassword(field: ChangePasswordRequestModel): ChangePasswordResponseModel {
        return try {
            val token = preferenceDataSource.getToken().first()
            val response = accountRemoteDataSource.changePassword(token, field.toData())
            response?.toDomain() ?: ChangePasswordResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }
}