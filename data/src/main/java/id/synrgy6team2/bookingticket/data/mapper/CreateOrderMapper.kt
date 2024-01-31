package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderRequest
import id.synrgy6team2.bookingticket.data.remote.model.CreateOrderResponse
import id.synrgy6team2.bookingticket.domain.model.CreateOrderRequestModel
import id.synrgy6team2.bookingticket.domain.model.CreateOrderResponseModel

fun CreateOrderRequestModel.mapToRequest(): CreateOrderRequest {
    return CreateOrderRequest(
        classId = this.classId,
        orderer = this.orderer?.mapToRequest(),
        passengerDetails = this.passengerDetails?.mapToRequest(),
        ticketId = this.ticketId
    )
}

fun CreateOrderRequestModel.Orderer?.mapToRequest(): CreateOrderRequest.Orderer {
    return CreateOrderRequest.Orderer(
        phoneNumber = this?.phoneNumber,
        fullName = this?.fullName,
        email = this?.email
    )
}

fun CreateOrderRequestModel.PassengerDetails?.mapToRequest(): CreateOrderRequest.PassengerDetails {
    return CreateOrderRequest.PassengerDetails(
        adult = this?.adult ?: emptyList(),
        infant = this?.infant ?: emptyList(),
        child = this?.child ?: emptyList()
    )
}

fun CreateOrderResponse.mapToModel(): CreateOrderResponseModel {
    return CreateOrderResponseModel(
        data = this.data?.mapToModel()
    )
}

fun CreateOrderResponse.Data?.mapToModel(): CreateOrderResponseModel.Data {
    return CreateOrderResponseModel.Data(
        orderId = this?.orderId,
        status = this?.status
    )
}

