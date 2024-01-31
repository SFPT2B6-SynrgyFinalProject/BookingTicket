package id.synrgy6team2.bookingticket.data.remote.service

import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderDetailResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderService {
    @POST("orders")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body field: CreateOrderRequest
    ): Response<CreateOrderResponse>

    @GET("orders")
    suspend fun getOrder(
        @Header("Authorization") token: String,
        @Query("status") status: String? = ""
    ): Response<GetOrderResponse>

    @GET("orders/details")
    suspend fun getOrderDetail(
        @Header("Authorization") token: String,
        @Query("orderId") orderId: String
    ): Response<GetOrderDetailResponse>

    @POST("orders/payment")
    suspend fun payOrder(
        @Header("Authorization") token: String,
        @Body field: PayOrderRequest
    ): Response<PayOrderResponse>
}