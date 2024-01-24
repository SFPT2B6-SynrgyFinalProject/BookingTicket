package id.synrgy6team2.bookingticket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAccount(field: ProfileEntity)

    @Query("SELECT * FROM tbl_account WHERE token=:token")
    fun getAccount(token: String): Flow<ProfileEntity>

    @Query("DELETE FROM tbl_account")
    suspend fun removeAccount()
}