package id.synrgy6team2.bookingticket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setNotification(field: List<NotificationEntity>)

    @Query("SELECT * FROM tbl_notification WHERE token=:token ORDER BY createdDatetime DESC")
    fun getNotification(token: String): Flow<List<NotificationEntity>>

    @Query("DELETE FROM tbl_notification")
    suspend fun removeNotification()
}