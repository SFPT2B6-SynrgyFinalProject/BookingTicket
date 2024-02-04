package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface NotificationLocalDataSource {
    suspend fun setNotification(field: List<NotificationEntity>)
    fun getNotification(token: String): Flow<List<NotificationEntity>>
    suspend fun removeNotification()
}