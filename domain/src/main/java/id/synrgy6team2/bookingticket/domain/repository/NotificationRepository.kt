package id.synrgy6team2.bookingticket.domain.repository

import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotif(): Flow<NotificationResponseModel>
}