package id.synrgy6team2.bookingticket.data.repository

import androidx.room.withTransaction
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.networkBoundResource
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.NotificationLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.mapper.toEntityList
import id.synrgy6team2.bookingticket.data.remote.datasource.NotificationRemoteDataSource
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import id.synrgy6team2.bookingticket.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val notificationRemoteDataSource: NotificationRemoteDataSource,
    private val notificationLocalDataSource: NotificationLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val room: RoomDB
) : NotificationRepository {
    override fun getNotif(): Flow<NotificationResponseModel> {
        return networkBoundResource(
            query = {
                val token = preferenceDataSource.getToken().first()
                notificationLocalDataSource.getNotification(token)
            },
            fetch = {
                val token = preferenceDataSource.getToken().first()
                notificationRemoteDataSource.getNotif(token)
            },
            saveFetchResult = { response ->
                val token = preferenceDataSource.getToken().first()
                room.withTransaction {
                    notificationLocalDataSource.removeNotification()
                    notificationLocalDataSource.setNotification(
                        response?.data.toEntityList(token)
                    )
                }
            }
        ).map { value: StateLocal<List<NotificationEntity>> ->
            NotificationResponseModel(data = value.data.toDomain())
        }
    }
}