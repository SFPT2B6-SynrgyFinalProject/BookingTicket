package id.synrgy6team2.bookingticket.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.room.withTransaction
import com.google.firebase.crashlytics.FirebaseCrashlytics
import id.synrgy6team2.bookingticket.common.StateLocal
import id.synrgy6team2.bookingticket.common.networkBoundResource
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.TicketLocalDataSource
import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import id.synrgy6team2.bookingticket.data.mapper.toData
import id.synrgy6team2.bookingticket.data.mapper.toDomain
import id.synrgy6team2.bookingticket.data.mapper.toLocal
import id.synrgy6team2.bookingticket.data.remote.datasource.TicketRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.model.AirportResponse
import id.synrgy6team2.bookingticket.data.remote.model.FlightClassResponse
import id.synrgy6team2.bookingticket.domain.model.AirportResponseModel
import id.synrgy6team2.bookingticket.domain.model.FlightClassResponseModel
import id.synrgy6team2.bookingticket.domain.model.TicketRequestModel
import id.synrgy6team2.bookingticket.domain.model.TicketResponseModel
import id.synrgy6team2.bookingticket.domain.repository.TicketRepository

class TicketRepositoryImpl(
    private val ticketRemoteDataSource: TicketRemoteDataSource,
    private val ticketLocalDataSource: TicketLocalDataSource,
    private val room: RoomDB
) : TicketRepository {
    override fun getFlightClass(): LiveData<FlightClassResponseModel> {
        return networkBoundResource(
            query = { ticketLocalDataSource.getFlightClass() },
            fetch = { ticketRemoteDataSource.getFlightClass() },
            saveFetchResult = { response: FlightClassResponse? ->
                room.withTransaction {
                    ticketLocalDataSource.removeFlightClass()
                    ticketLocalDataSource.setFlightClass(response?.data.toLocal())
                }
            }
        ).asLiveData().map { state: StateLocal<List<FlightClassEntity>> ->
            FlightClassResponseModel(data = state.data.toDomain())
        }
    }

    override fun getAirport(query: String?): LiveData<AirportResponseModel> {
        return networkBoundResource(
            query = { ticketLocalDataSource.getAirport() },
            fetch = { ticketRemoteDataSource.getAirport(query) },
            saveFetchResult = { response: AirportResponse? ->
                room.withTransaction {
                    ticketLocalDataSource.removeAirport()
                    ticketLocalDataSource.setAirport(response?.data.toLocal())
                }
            }
        ).asLiveData().map { state: StateLocal<List<AirportEntity>> ->
            AirportResponseModel(data = state.data.toDomain())
        }
    }

    override suspend fun getTicket(field: TicketRequestModel): TicketResponseModel {
        return try {
            val response = ticketRemoteDataSource.getTicket(field.toData())
            response?.toDomain() ?: TicketResponseModel()
        } catch (e: Exception) {
            throw Exception(e.message ?: "")
        }
    }
}