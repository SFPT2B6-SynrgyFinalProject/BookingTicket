package id.synrgy6team2.bookingticket.data.mapper

import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel

fun List<FlightClassResponse.DataItem?>?.toLocal(): List<FlightClassEntity> {
    val list = mutableListOf<FlightClassEntity>()
    this?.forEach { it: FlightClassResponse.DataItem? ->
        list.add(
            FlightClassEntity(it?.id, it?.name)
        )
    }
    return list
}

fun List<FlightClassEntity>?.toDomain(): List<FlightClassResponseModel.DataItem> {
    val list = mutableListOf<FlightClassResponseModel.DataItem>()
    this?.forEach { it: FlightClassEntity ->
        list.add(
            FlightClassResponseModel.DataItem(id = it.id, name = it.name)
        )
    }
    return list
}