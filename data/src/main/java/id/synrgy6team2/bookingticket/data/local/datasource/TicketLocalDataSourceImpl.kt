package id.synrgy6team2.bookingticket.data.local.datasource

import id.synrgy6team2.bookingticket.data.local.dao.TicketDao
import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import kotlinx.coroutines.flow.Flow

class TicketLocalDataSourceImpl(
    private val local: TicketDao
) : TicketLocalDataSource {
    override suspend fun setFlightClass(field: List<FlightClassEntity>) {
        local.setFlightClass(field)
    }

    override fun getFlightClass(): Flow<List<FlightClassEntity>> {
        return local.getFlightClass()
    }

    override suspend fun removeFlightClass() {
        local.removeFlightClass()
    }

    override suspend fun setAirport(field: List<AirportEntity>) {
        local.setAirport(field)
    }

    override fun getAirport(): Flow<List<AirportEntity>> {
        return getAirport()
    }

    override suspend fun removeAirport() {
        local.removeAirport()
    }
}