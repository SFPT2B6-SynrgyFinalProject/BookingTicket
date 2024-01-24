package id.synrgy6team2.bookingticket.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.dao.TicketDao
import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity

@Database(entities = [
    ProfileEntity::class,
    FlightClassEntity::class,
    AirportEntity::class
], version = 2, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun ticketDao(): TicketDao
}