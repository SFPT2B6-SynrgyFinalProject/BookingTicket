package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.remote.model.TicketRequest
import id.synrgy6team2.bookingticket.data.remote.model.TicketResponse
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel

fun TicketRequestModel.toData(): TicketRequest {
    return TicketRequest(classId, dataPerPage, passenger?.toData(), departureCode, sortBy, page, departureDateStart, arrivalCode, departureDateEnd, airlineId)
}

fun TicketRequestModel.Passenger.toData(): TicketRequest.Passenger {
    return TicketRequest.Passenger(adult, infant, child)
}

fun TicketResponse.toDomain(): TicketResponseModel {
    return TicketResponseModel(data?.toDomain(), status)
}

fun TicketResponse.Data.toDomain(): TicketResponseModel.Data {
    return TicketResponseModel.Data(availableFlight.toFlightDomain())
}

fun List<TicketResponse.Data.AvailableFlightItem?>?.toFlightDomain(): List<TicketResponseModel.Data.AvailableFlightItem> {
    val list = mutableListOf<TicketResponseModel.Data.AvailableFlightItem>()
    this?.forEach {
        TicketResponseModel.Data.AvailableFlightItem(it?.arrivalDateTime, it?.withFood, it?.discountedPricePerPerson, it?.durationInMin, it?.luggage, it?.withLuggage, it?.departureDateTime, it?.arrivalAirportCode, it?.airline?.toDomain(), it?.ticketId, it?.departureAirportCode, it?.basePricePerPerson)
    }
    return list
}

fun TicketResponse.Data.AvailableFlightItem.Airline.toDomain(): TicketResponseModel.Data.AvailableFlightItem.Airline {
    return TicketResponseModel.Data.AvailableFlightItem.Airline(name, iconUrl, airlineId)
}