package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

class AccountLocalDataSourceImpl(
    private val local: AccountDao
) : AccountLocalDataSource {
    override suspend fun setAccount(field: ProfileEntity) {
        local.setAccount(field)
    }

    override fun getAccount(token: String): Flow<ProfileEntity> {
        return local.getAccount(token)
    }

    override suspend fun removeAccount() {
        local.removeAccount()
    }
}