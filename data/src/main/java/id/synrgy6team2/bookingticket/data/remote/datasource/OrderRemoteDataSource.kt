package id.synrgy6team2.bookingticket.data.remote.datasource

import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderDetailResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderResponse

interface OrderRemoteDataSource {
    suspend fun createOrder(token: String, field: CreateOrderRequest): CreateOrderResponse?
    suspend fun getOrder(token: String, query: String?): GetOrderResponse?
    suspend fun getOrderDetail(token: String, orderId: String): GetOrderDetailResponse?
    suspend fun payOrder(token: String, field: PayOrderRequest): PayOrderResponse?
}