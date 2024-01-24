package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import kotlinx.coroutines.flow.Flow

interface TicketLocalDataSource {
    suspend fun setFlightClass(field: List<FlightClassEntity>)
    fun getFlightClass(): Flow<List<FlightClassEntity>>
    suspend fun removeFlightClass()

    suspend fun setAirport(field: List<AirportEntity>)
    fun getAirport(): Flow<List<AirportEntity>>
    suspend fun removeAirport()
}