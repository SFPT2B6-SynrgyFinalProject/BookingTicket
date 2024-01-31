package id.synrgy6team2.bookingticket.data.remote.datasource

import com.google.gson.Gson
import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderDetailResponse
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderResponse
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderResponse
import id.synrgy6team2.bookingticket.data.remote.service.OrderService

class OrderRemoteDateSourceImpl(
    private val service: OrderService
) : OrderRemoteDataSource {
    private data class ErrorMessage(
        val data: ResultItem? = null,
        val status: String? = null,
        val message: String? = null
    ) {
        data class ResultItem(
            val ticketId: String? = null,
            val classId: String? = null,
            val orderer: Orderer? = null,
            val passengerDetails: PassengerDetails? = null,
            val authentication: String? = null,
            val orderId: String? = null,
            val status: String? = null,
            val cardNumber: String? = null,
            val cardName: String? = null,
            val cvv: String? = null,
            val expiredDate: String? = null
        ) {
            data class Orderer(
                val fullName: String? = null,
                val phoneNumber: String? = null,
                val email: String? = null
            )

            data class PassengerDetails(
                val adult: String? = null,
                val child: String? = null,
                val infant: String? = null
            )
        }
    }

    private fun specificErrorMessage(message: ErrorMessage): String {
        return when {
            !message.data?.authentication.isNullOrEmpty() -> message.data?.authentication
            !message.data?.orderer?.fullName.isNullOrEmpty() -> message.data?.orderer?.fullName
            !message.data?.orderer?.phoneNumber.isNullOrEmpty() -> message.data?.orderer?.phoneNumber
            !message.data?.orderer?.email.isNullOrEmpty() -> message.data?.orderer?.email
            !message.data?.passengerDetails?.adult.isNullOrEmpty() -> message.data?.passengerDetails?.adult
            !message.data?.passengerDetails?.child.isNullOrEmpty() -> message.data?.passengerDetails?.child
            !message.data?.passengerDetails?.infant.isNullOrEmpty() -> message.data?.passengerDetails?.infant
            !message.data?.classId.isNullOrEmpty() -> message.data?.classId
            !message.data?.ticketId.isNullOrEmpty() -> message.data?.ticketId
            !message.data?.orderId.isNullOrEmpty() -> message.data?.orderId
            !message.data?.status.isNullOrEmpty() -> message.data?.status
            !message.data?.cardNumber.isNullOrEmpty() -> message.data?.cardNumber
            !message.data?.cardName.isNullOrEmpty() -> message.data?.cardName
            !message.data?.cvv.isNullOrEmpty() -> message.data?.cvv
            !message.data?.expiredDate.isNullOrEmpty() -> message.data?.expiredDate
            else -> "Terjadi kesalahan"
        } ?: "Terjadi kesalahan pada server"
    }

    override suspend fun createOrder(
        token: String,
        field: CreateOrderRequest
    ): CreateOrderResponse? {
        val response = service.createOrder("Bearer $token", field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }

    override suspend fun getOrder(token: String, query: String?): GetOrderResponse? {
        val response = service.getOrder("Bearer $token", query)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }

    override suspend fun getOrderDetail(token: String, orderId: String): GetOrderDetailResponse? {
        val response = service.getOrderDetail("Bearer $token", orderId)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }

    override suspend fun payOrder(token: String, field: PayOrderRequest): PayOrderResponse? {
        val response = service.payOrder("Bearer $token", field)
        return if (response.isSuccessful) {
            response.body()
        } else {
            val code = response.code()
            val message = Gson().fromJson(response.errorBody()?.string(), ErrorMessage::class.java)
            throw when (code) {
                in 400..499 -> Exception("$code - ${specificErrorMessage(message)}")
                else -> Exception("$code - Terjadi kesalahan!")
            }
        }
    }
}