package id.synrgy6team2.bookingticket.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.entity.ProfileEntity

@Database(entities = [
    ProfileEntity::class
], version = 1, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract fun accountDao(): AccountDao
}