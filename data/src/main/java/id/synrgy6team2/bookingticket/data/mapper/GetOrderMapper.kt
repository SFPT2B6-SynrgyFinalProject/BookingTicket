package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.GetOrderResponse
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel

fun GetOrderResponse.mapToModel(): GetOrderResponseModel {
    return GetOrderResponseModel(
        data = this.data.mapToModel(),
        message = this.message,
        status = this.status
    )
}

fun GetOrderResponse.Data?.mapToModel(): GetOrderResponseModel.Data {
    return GetOrderResponseModel.Data(
        orders = this?.orders.mapOrdersList()
    )
}

fun List<GetOrderResponse.Data.OrdersItem?>?.mapOrdersList(): List<GetOrderResponseModel.Data.OrdersItem> {
    return this?.map { it.mapOrdersItem() } ?: emptyList()
}

fun GetOrderResponse.Data.OrdersItem?.mapOrdersItem(): GetOrderResponseModel.Data.OrdersItem {
    return GetOrderResponseModel.Data.OrdersItem(
        totalPassengers = this?.totalPassengers,
        flightCode = this?.flightCode,
        arrival = this?.arrival.mapArrival(),
        orderId = this?.orderId,
        departure = this?.departure.mapDeparture(),
        airline = this?.airline.mapAirline(),
        paymentStatus = this?.paymentStatus
    )
}

fun GetOrderResponse.Data.OrdersItem.Arrival?.mapArrival(): GetOrderResponseModel.Data.OrdersItem.Arrival {
    return GetOrderResponseModel.Data.OrdersItem.Arrival(
        dateTime = this?.dateTime,
        airportName = this?.airportName,
        city = this?.city,
        airportId = this?.airportId,
        code = this?.code
    )
}

fun GetOrderResponse.Data.OrdersItem.Departure?.mapDeparture(): GetOrderResponseModel.Data.OrdersItem.Departure {
    return GetOrderResponseModel.Data.OrdersItem.Departure(
        dateTime = this?.dateTime,
        airportName = this?.airportName,
        city = this?.city,
        airportId = this?.airportId,
        code = this?.code
    )
}

fun GetOrderResponse.Data.OrdersItem.Airline?.mapAirline(): GetOrderResponseModel.Data.OrdersItem.Airline {
    return GetOrderResponseModel.Data.OrdersItem.Airline(
        name = this?.name,
        iconUrl = this?.iconUrl,
        airlineId = this?.airlineId
    )
}
