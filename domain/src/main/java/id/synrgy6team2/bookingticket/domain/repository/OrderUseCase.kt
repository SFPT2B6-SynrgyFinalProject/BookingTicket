package id.synrgy6team2.bookingticket.domain.repository

import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel

class OrderUseCase(private val repository: OrderRepository) {
    suspend fun createOrder(field: CreateOrderRequestModel): CreateOrderResponseModel {
        return repository.createOrder(field)
    }

    suspend fun getOrder(query: String?): GetOrderResponseModel {
        return repository.getOrder(query)
    }

    suspend fun getOrderDetail(orderId: String): GetOrderDetailResponseModel {
        return repository.getOrderDetail(orderId)
    }

    suspend fun payOrder(field: PayOrderRequestModel): PayOrderResponseModel {
        return repository.payOrder(field)
    }
}