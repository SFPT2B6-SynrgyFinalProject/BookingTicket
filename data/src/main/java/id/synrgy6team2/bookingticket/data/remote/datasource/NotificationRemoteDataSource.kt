package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.NotificationResponse

interface NotificationRemoteDataSource {
    suspend fun getNotif(token: String): NotificationResponse?
}