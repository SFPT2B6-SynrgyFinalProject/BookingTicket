package id.synrgy6team2.bookingticket.data.repository

import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.mapper.mapToModel
import id.synrgy6team2.bookingticket.data.mapper.mapToRequest
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.remote.datasource.OrderRemoteDataSource
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel
import id.synrgy6team2.bookingticket.domain.repository.OrderRepository
import kotlinx.coroutines.flow.first

class OrderRepositoryImpl(
    private val orderDataSource: OrderRemoteDataSource,
    private val preferenceDataSource: PreferenceDataSource
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

    override suspend fun getOrder(query: String?): GetOrderResponseModel {
        return try {
            val token = preferenceDataSource.getToken().first()
            val response = orderDataSource.getOrder(token, query)
            response?.mapToModel() ?: GetOrderResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
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