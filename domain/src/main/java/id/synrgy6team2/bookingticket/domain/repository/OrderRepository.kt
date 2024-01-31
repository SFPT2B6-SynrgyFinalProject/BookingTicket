package id.synrgy6team2.bookingticket.domain.repository

import androidx.lifecycle.LiveData
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel

interface OrderRepository {
    suspend fun createOrder(field: CreateOrderRequestModel): CreateOrderResponseModel
    suspend fun getOrder(query: String?): GetOrderResponseModel
    suspend fun getOrderDetail(orderId: String): GetOrderDetailResponseModel
    suspend fun payOrder(field: PayOrderRequestModel): PayOrderResponseModel
}