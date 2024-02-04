package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.dao.OrderDao
import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

class OrderLocalDataSourceImpl(
    private val local: OrderDao
) : OrderLocalDataSource {
    override suspend fun setHistoryOrder(field: List<OrderEntity>) {
        local.setHistoryOrder(field)
    }

    override fun getHistoryOrder(token: String, status: String?): Flow<List<OrderEntity>> {
        return local.getHistoryOrder(token, status ?: "")
    }

    override suspend fun removeHistoryOrder() {
        local.removeHistoryOrder()
    }

    override suspend fun removeHistorySomeOrder(status: String?) {
        local.removeHistorySomeOrder(status ?: "")
    }
}