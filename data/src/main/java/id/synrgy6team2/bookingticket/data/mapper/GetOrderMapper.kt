package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import id.synrgy6team2.bookingticket.data.remote.model.GetOrderResponse
import id.synrgy6team2.bookingticket.domain.model.GetOrderResponseModel

fun GetOrderResponse.Data?.toEntity(token: String, status: String?): List<OrderEntity> {
    return this?.orders?.map { orderItem ->
        OrderEntity(
            totalPassengers = orderItem.totalPassengers,
            flightCode = orderItem.flightCode,
            arrival = orderItem.arrival?.toArrivalEntity(),
            orderId = orderItem.orderId.toString(),
            departure = orderItem.departure?.toDepartureEntity(),
            airline = orderItem.airline?.toAirlineEntity(),
            paymentStatus = orderItem.paymentStatus,
            token = token,
            status = status
        )
    } ?: emptyList()
}

fun GetOrderResponse.Data.OrdersItem.Arrival?.toArrivalEntity(): OrderEntity.Arrival {
    return OrderEntity.Arrival(
        dateTime = this?.dateTime,
        airportName = this?.airportName,
        city = this?.city,
        airportId = this?.airportId,
        code = this?.code
    )
}

fun GetOrderResponse.Data.OrdersItem.Departure?.toDepartureEntity(): OrderEntity.Departure {
    return OrderEntity.Departure(
        dateTime = this?.dateTime,
        airportName = this?.airportName,
        city = this?.city,
        airportId = this?.airportId,
        code = this?.code
    )
}

fun GetOrderResponse.Data.OrdersItem.Airline?.toAirlineEntity(): OrderEntity.Airline {
    return OrderEntity.Airline(
        name = this?.name,
        iconUrl = this?.iconUrl,
        airlineId = this?.airlineId
    )
}

fun List<OrderEntity>.toDomain(): GetOrderResponseModel {
    val ordersItems = this.map {
        GetOrderResponseModel.Data.OrdersItem(
            totalPassengers = it.totalPassengers,
            flightCode = it.flightCode,
            arrival = GetOrderResponseModel.Data.OrdersItem.Arrival(
                dateTime = it.arrival?.dateTime,
                airportName = it.arrival?.airportName,
                city = it.arrival?.city,
                airportId = it.arrival?.airportId,
                code = it.arrival?.code
            ),
            orderId = it.orderId,
            departure = GetOrderResponseModel.Data.OrdersItem.Departure(
                dateTime = it.departure?.dateTime,
                airportName = it.departure?.airportName,
                city = it.departure?.city,
                airportId = it.departure?.airportId,
                code = it.departure?.code
            ),
            airline = GetOrderResponseModel.Data.OrdersItem.Airline(
                name = it.airline?.name,
                iconUrl = it.airline?.iconUrl,
                airlineId = it.airline?.airlineId
            ),
            paymentStatus = it.paymentStatus
        )
    }

    return GetOrderResponseModel(data = GetOrderResponseModel.Data(orders = ordersItems))
}

