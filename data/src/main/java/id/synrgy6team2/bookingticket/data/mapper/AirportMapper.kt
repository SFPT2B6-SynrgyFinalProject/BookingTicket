package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel

fun List<AirportResponse.DataItem?>?.toLocal(): List<AirportEntity> {
    val list = mutableListOf<AirportEntity>()
    this?.forEach { it: AirportResponse.DataItem? ->
        list.add(
            AirportEntity(it?.id, it?.airportName, it?.cityName, it?.code)
        )
    }
    return list
}

fun List<AirportEntity>?.toDomain(): List<AirportResponseModel.DataItem> {
    val list = mutableListOf<AirportResponseModel.DataItem>()
    this?.forEach { it: AirportEntity ->
        list.add(
            AirportResponseModel.DataItem(it.airportName, it.code, it.cityName, it.id)
        )
    }
    return list
}