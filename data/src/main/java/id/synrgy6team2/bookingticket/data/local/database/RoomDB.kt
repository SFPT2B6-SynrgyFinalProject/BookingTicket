package id.synrgy6team2.bookingticket.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.dao.NotificationDao
import id.synrgy6team2.bookingticket.data.local.dao.OrderDao
import id.synrgy6team2.bookingticket.data.local.dao.TicketDao
import id.synrgy6team2.bookingticket.data.local.entity.AirportEntity
import id.synrgy6team2.bookingticket.data.local.entity.FlightClassEntity
import id.synrgy6team2.bookingticket.data.local.entity.NotificationEntity
import id.synrgy6team2.bookingticket.data.local.entity.OrderEntity
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity
import id.synrgy6team2.bookingticket.data.local.utils.OrderTypeConverter

@Database(entities = [
    ProfileEntity::class,
    FlightClassEntity::class,
    AirportEntity::class,
    NotificationEntity::class,
    OrderEntity::class
], version = 5, exportSchema = false)
@TypeConverters(OrderTypeConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun ticketDao(): TicketDao
    abstract fun notificationDao(): NotificationDao
    abstract fun orderDao(): OrderDao
}