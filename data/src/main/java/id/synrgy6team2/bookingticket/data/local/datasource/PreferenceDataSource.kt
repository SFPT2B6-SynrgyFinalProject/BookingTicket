package id.synrgy6team2.bookingticket.data.local.datasource

import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setToken(value: String)
    fun getToken(): Flow<String>
}