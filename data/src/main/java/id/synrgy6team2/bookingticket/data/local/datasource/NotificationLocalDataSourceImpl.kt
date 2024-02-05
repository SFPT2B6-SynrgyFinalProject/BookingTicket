package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.dao.NotificationDao
import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

class NotificationLocalDataSourceImpl(
    private val local: NotificationDao
) : NotificationLocalDataSource {
    override suspend fun setNotification(field: List<NotificationEntity>) {
        local.setNotification(field)
    }

    override fun getNotification(token: String): Flow<List<NotificationEntity>> {
        return local.getNotification(token)
    }

    override suspend fun removeNotification() {
        local.removeNotification()
    }
}