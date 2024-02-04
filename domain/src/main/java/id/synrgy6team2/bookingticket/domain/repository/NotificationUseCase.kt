package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.domain.model.NotificationResponseModel
import kotlinx.coroutines.flow.Flow

class NotificationUseCase(private val repository: NotificationRepository) {
    fun getNotif(): Flow<NotificationResponseModel> {
        return repository.getNotif()
    }
}