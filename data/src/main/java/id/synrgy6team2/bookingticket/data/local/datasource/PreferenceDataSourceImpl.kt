package id.synrgy6team2.bookingticket.data.local.datasource

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.synrgy6team2.bookingticket.common.getValue
import id.synrgy6team2.bookingticket.common.setValue
import kotlinx.coroutines.flow.Flow

class PreferenceDataSourceImpl(
    private val context: Context
) : PreferenceDataSource {
    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            DATASTORE_SETTINGS
        )
        private val LOGIN = booleanPreferencesKey("LOGIN")
        private val TOKEN = stringPreferencesKey("TOKEN")
        private val EXPIRE_VERIFY = longPreferencesKey("EXPIRE_VERIFY")
        private val COUNT_VERIFY = intPreferencesKey("COUNT_VERIFY")
    }

    override suspend fun setLogin(value: Boolean) {
        context.dataStore.setValue(LOGIN, value)
    }

    override fun getLogin(): Flow<Boolean> {
        return context.dataStore.getValue(LOGIN, false)
    }

    override suspend fun setToken(value: String) {
        context.dataStore.setValue(TOKEN, value)
    }

    override fun getToken(): Flow<String> {
        return context.dataStore.getValue(TOKEN, "")
    }

    override suspend fun setCountVerify(value: Int) {
        context.dataStore.setValue(COUNT_VERIFY, value)
    }

    override fun getCountVerify(): Flow<Int> {
        return context.dataStore.getValue(COUNT_VERIFY, -1)
    }
}