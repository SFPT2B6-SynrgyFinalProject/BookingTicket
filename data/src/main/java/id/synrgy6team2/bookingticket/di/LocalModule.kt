package id.synrgy6team2.bookingticket.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.dao.TicketDao
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomDB {
        return Room.databaseBuilder(
            context.applicationContext,
            RoomDB::class.java,
            "db_app"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAccountDao(roomDB: RoomDB): AccountDao {
        return roomDB.accountDao()
    }

    @Provides
    fun provideTicketDao(roomDB: RoomDB): TicketDao {
        return roomDB.ticketDao()
    }
}