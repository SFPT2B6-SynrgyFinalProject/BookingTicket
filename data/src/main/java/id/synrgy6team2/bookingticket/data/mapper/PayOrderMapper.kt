package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.PayOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.PayOrderResponse
import id.synrgy6team2.bookingticket.domain.model.PayOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.PayOrderResponseModel

fun PayOrderRequestModel.toData(): PayOrderRequest {
    return PayOrderRequest(
        cvv = this.cvv,
        cardName = this.cardName,
        orderId = this.orderId,
        expiredDate = this.expiredDate,
        cardNumber = this.cardNumber
    )
}

fun PayOrderResponse.toDomain(): PayOrderResponseModel {
    return PayOrderResponseModel(
        data = this.data?.let { PayOrderResponseModel.Data(orderId = it.orderId) },
        message = this.message,
        status = this.status
    )
}