package id.synrgy6team2.bookingticket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setFlightClass(field: List<FlightClassEntity>)

    @Query("SELECT * FROM tbl_flight_type")
    fun getFlightClass(): Flow<List<FlightClassEntity>>

    @Query("DELETE FROM tbl_flight_type")
    suspend fun removeFlightClass()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAirport(field: List<AirportEntity>)

    @Query("SELECT * FROM tbl_airport")
    fun getAirport(): Flow<List<AirportEntity>>

    @Query("DELETE FROM tbl_airport")
    suspend fun removeAirport()
}