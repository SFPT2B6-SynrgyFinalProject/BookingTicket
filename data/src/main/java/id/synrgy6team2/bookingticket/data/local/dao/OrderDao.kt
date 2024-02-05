package id.synrgy6team2.bookingticket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setHistoryOrder(field: List<OrderEntity>)

    @Query("SELECT * FROM tbl_order WHERE token=:token AND status=:status ORDER BY departure DESC")
    fun getHistoryOrder(token: String, status: String): Flow<List<OrderEntity>>

    @Query("DELETE FROM tbl_order")
    suspend fun removeHistoryOrder()

    @Query("DELETE FROM tbl_order WHERE status=:status")
    suspend fun removeHistorySomeOrder(status: String)
}