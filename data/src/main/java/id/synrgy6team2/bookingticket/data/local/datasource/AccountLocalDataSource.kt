package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface AccountLocalDataSource {
    suspend fun setAccount(field: ProfileEntity)
    fun getAccount(token: String): Flow<ProfileEntity>

    suspend fun removeAccount(token: String)

    suspend fun logout()
}