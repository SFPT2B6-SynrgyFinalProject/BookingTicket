package id.synrgy6team2.bookingticket.data.local.datasource

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setToken(value: String)
    fun getToken(): Flow<String>
    suspend fun setExpireVerify(timestamp: Long)
    fun getExpireVerify(): Flow<Long>
    suspend fun setCountVerify(value: Int)
    fun getCountVerify(): Flow<Int>
}