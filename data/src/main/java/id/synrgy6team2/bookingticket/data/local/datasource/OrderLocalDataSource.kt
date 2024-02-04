package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

interface OrderLocalDataSource {
    suspend fun setHistoryOrder(field: List<OrderEntity>)
    fun getHistoryOrder(token: String, status: String?): Flow<List<OrderEntity>>
    suspend fun removeHistoryOrder()
    suspend fun removeHistorySomeOrder(status: String?)
}