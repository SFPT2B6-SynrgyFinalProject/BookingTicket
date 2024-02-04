package id.synrgy6team2.bookingticket.data.repository

import androidx.room.withTransaction
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.networkBoundResource
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.OrderLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import id.synrgy6team2.bookingticket.data.mapper.mapToModel
import id.synrgy6team2.bookingticket.data.mapper.mapToRequest
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.mapper.toEntity
import id.synrgy6team2.bookingticket.data.remote.datasource.OrderRemoteDataSource
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class OrderRepositoryImpl(
    private val orderDataSource: OrderRemoteDataSource,
    private val orderLocal: OrderLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val roomDB: RoomDB
) : OrderRepository {
    override suspend fun createOrder(field: CreateOrderRequestModel): CreateOrderResponseModel {
        return try {
            val token = preferenceDataSource.getToken().first()
            val response = orderDataSource.createOrder(token, field.mapToRequest())
            response?.mapToModel() ?: CreateOrderResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override fun getOrder(query: String?): Flow<GetOrderResponseModel> {
        return networkBoundResource(
            query = {
                val token = preferenceDataSource.getToken().first()
                orderLocal.getHistoryOrder(token, query)
            },
            fetch = {
                val token = preferenceDataSource.getToken().first()
                orderDataSource.getOrder(token, query)
            },
            saveFetchResult = { response ->
                val token = preferenceDataSource.getToken().first()
                roomDB.withTransaction {
                    orderLocal.removeHistorySomeOrder(query)
                    orderLocal.setHistoryOrder(
                        response?.data.toEntity(token, query)
                    )
                }
            }
        ).map { value: StateLocal<List<OrderEntity>> ->
            GetOrderResponseModel(data = value.data?.toDomain()?.data)
        }
    }

    override suspend fun getOrderDetail(orderId: String): GetOrderDetailResponseModel {
        return try {
            val token = preferenceDataSource.getToken().first()
            val response = orderDataSource.getOrderDetail(token, orderId)
            response?.toDomain() ?: GetOrderDetailResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }

    override suspend fun payOrder(field: PayOrderRequestModel): PayOrderResponseModel {
        return try {
            val token = preferenceDataSource.getToken().first()
            val response = orderDataSource.payOrder(token, field.toData())
            response?.toDomain() ?: PayOrderResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }
}