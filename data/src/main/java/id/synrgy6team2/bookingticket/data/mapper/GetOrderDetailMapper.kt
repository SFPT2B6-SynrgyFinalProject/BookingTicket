package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.GetOrderDetailResponse
import id.synrgy6team2.bookingticket.domain.model.GetOrderDetailResponseModel

fun GetOrderDetailResponse.toDomain(): GetOrderDetailResponseModel {
    return GetOrderDetailResponseModel(
        data = data?.toDataResponseModel(),
        message = message,
        status = status
    )
}

fun GetOrderDetailResponse.Data?.toDataResponseModel(): GetOrderDetailResponseModel.Data? {
    return this?.let {
        GetOrderDetailResponseModel.Data(
            orderId = orderId,
            flightDetails = flightDetails?.toFlightDetailsResponseModel(),
            luggage = luggage,
            orderer = orderer?.toOrdererResponseModel(),
            priceDetails = priceDetails?.toPriceDetailsResponseModel(),
            passengerDetails = passengerDetails?.toPassengerDetailsResponseModel(),
            paymentStatus = paymentStatus,
            flightClass = flightClass
        )
    }
}

fun GetOrderDetailResponse.Data.PriceDetails?.toPriceDetailsResponseModel(): GetOrderDetailResponseModel.Data.PriceDetails? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PriceDetails(
            total = total,
            tax = tax,
            basePriceBreakdown = basePriceBreakdown?.toBasePriceBreakdownResponseModel(),
            totalDicount = totalDicount
        )
    }
}

fun GetOrderDetailResponse.Data.PriceDetails.BasePriceBreakdown?.toBasePriceBreakdownResponseModel(): GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown(
            adult = adult?.toAdultResponseModel(),
            infant = infant?.toInfantResponseModel(),
            child = child?.toChildResponseModel()
        )
    }
}

fun GetOrderDetailResponse.Data.PriceDetails.BasePriceBreakdown.Infant?.toInfantResponseModel(): GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Infant? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Infant(
            passengerCount = passengerCount,
            price = price
        )
    }
}

fun GetOrderDetailResponse.Data.PriceDetails.BasePriceBreakdown.Child?.toChildResponseModel(): GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Child? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Child(
            passengerCount = passengerCount,
            price = price
        )
    }
}

fun GetOrderDetailResponse.Data.PriceDetails.BasePriceBreakdown.Adult?.toAdultResponseModel(): GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Adult? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PriceDetails.BasePriceBreakdown.Adult(
            passengerCount = passengerCount,
            price = price
        )
    }
}

fun GetOrderDetailResponse.Data.FlightDetails?.toFlightDetailsResponseModel(): GetOrderDetailResponseModel.Data.FlightDetails? {
    return this?.let {
        GetOrderDetailResponseModel.Data.FlightDetails(
            flightCode = flightCode,
            arrival = arrival?.toArrivalResponseModel(),
            departure = departure?.toDepartureResponseModel(),
            airline = airline?.toAirlineResponseModel()
        )
    }
}

fun GetOrderDetailResponse.Data.FlightDetails.Arrival?.toArrivalResponseModel(): GetOrderDetailResponseModel.Data.FlightDetails.Arrival? {
    return this?.let {
        GetOrderDetailResponseModel.Data.FlightDetails.Arrival(
            dateTime = dateTime,
            airportName = airportName,
            city = city,
            airportId = airportId,
            code = code
        )
    }
}

fun GetOrderDetailResponse.Data.FlightDetails.Departure?.toDepartureResponseModel(): GetOrderDetailResponseModel.Data.FlightDetails.Departure? {
    return this?.let {
        GetOrderDetailResponseModel.Data.FlightDetails.Departure(
            dateTime = dateTime,
            airportName = airportName,
            city = city,
            airportId = airportId,
            code = code
        )
    }
}

fun GetOrderDetailResponse.Data.FlightDetails.Airline?.toAirlineResponseModel(): GetOrderDetailResponseModel.Data.FlightDetails.Airline? {
    return this?.let {
        GetOrderDetailResponseModel.Data.FlightDetails.Airline(
            name = name,
            iconUrl = iconUrl,
            airlineId = airlineId
        )
    }
}

fun GetOrderDetailResponse.Data.PassengerDetails?.toPassengerDetailsResponseModel(): GetOrderDetailResponseModel.Data.PassengerDetails? {
    return this?.let {
        GetOrderDetailResponseModel.Data.PassengerDetails(
            adult = adult.orEmpty(),
            infant = infant.orEmpty(),
            child = child.orEmpty(),
            passengerTotal = passengerTotal
        )
    }
}

fun GetOrderDetailResponse.Data.Orderer?.toOrdererResponseModel(): GetOrderDetailResponseModel.Data.Orderer? {
    return this?.let {
        GetOrderDetailResponseModel.Data.Orderer(
            phoneNumber = phoneNumber,
            fullName = fullName,
            email = email
        )
    }
}