package id.synrgy6team2.bookingticket.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.dao.NotificationDao
import id.synrgy6team2.bookingticket.data.local.dao.OrderDao
import id.synrgy6team2.bookingticket.data.local.dao.TicketDao
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSourceImpl
import id.synrgy6team2.bookingticket.data.local.datasource.NotificationLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.NotificationLocalDataSourceImpl
import id.synrgy6team2.bookingticket.data.local.datasource.OrderLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.OrderLocalDataSourceImpl
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSourceImpl
import id.synrgy6team2.bookingticket.data.local.datasource.TicketLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.TicketLocalDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.AccountRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.AccountRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.NotificationRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.NotificationRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.OrderRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.OrderRemoteDateSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.TicketRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.TicketRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.service.AccountService
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import id.synrgy6team2.bookingticket.data.remote.service.NotificationService
import id.synrgy6team2.bookingticket.data.remote.service.OrderService
import id.synrgy6team2.bookingticket.data.remote.service.TicketService
import id.synrgy6team2.bookingticket.data.repository.AccountRepositoryImpl
import id.synrgy6team2.bookingticket.data.repository.AuthenticationRepositoryImpl
import id.synrgy6team2.bookingticket.data.repository.NotificationRepositoryImpl
import id.synrgy6team2.bookingticket.data.repository.OrderRepositoryImpl
import id.synrgy6team2.bookingticket.data.repository.TicketRepositoryImpl
import id.synrgy6team2.bookingticket.domain.repository.AccountRepository
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import id.synrgy6team2.bookingticket.domain.repository.NotificationRepository
import id.synrgy6team2.bookingticket.domain.repository.OrderRepository
import id.synrgy6team2.bookingticket.domain.repository.TicketRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAuthenticationRemoteDataSourceImpl(
        authenticationService: AuthenticationService
    ): AuthenticationRemoteDataSource {
        return AuthenticationRemoteDataSourceImpl(authenticationService)
    }

    @Singleton
    @Provides
    fun provideAccountRemoteDataSourceImpl(
        accountService: AccountService
    ): AccountRemoteDataSource {
        return AccountRemoteDataSourceImpl(accountService)
    }

    @Singleton
    @Provides
    fun provideTicketRemoteDataSourceImpl(
        ticketService: TicketService
    ): TicketRemoteDataSource {
        return TicketRemoteDataSourceImpl(ticketService)
    }

    @Singleton
    @Provides
    fun provideOrderRemoteDataSourceImpl(
        orderService: OrderService
    ): OrderRemoteDataSource {
        return OrderRemoteDateSourceImpl(orderService)
    }

    @Singleton
    @Provides
    fun provideNotificationRemoteDataSourceImpl(
        notificationService: NotificationService
    ): NotificationRemoteDataSource {
        return NotificationRemoteDataSourceImpl(notificationService)
    }

    @Singleton
    @Provides
    fun provideTicketLocalDataSourceImpl(
        ticketDao: TicketDao
    ): TicketLocalDataSource {
        return TicketLocalDataSourceImpl(ticketDao)
    }

    @Singleton
    @Provides
    fun provideAccountLocalDataSourceImpl(
        accountDao: AccountDao
    ): AccountLocalDataSource {
        return AccountLocalDataSourceImpl(accountDao)
    }

    @Singleton
    @Provides
    fun provideNotificationLocalDataSourceImpl(
        notificationDao: NotificationDao
    ): NotificationLocalDataSource {
        return NotificationLocalDataSourceImpl(notificationDao)
    }

    @Singleton
    @Provides
    fun provideOrderLocalDataSourceImpl(
        orderDao: OrderDao
    ): OrderLocalDataSource {
        return OrderLocalDataSourceImpl(orderDao)
    }


    /**
     * Local
     * */
    @Singleton
    @Provides
    fun providePreferenceDataSourceImpl(
        @ApplicationContext context: Context
    ): PreferenceDataSource {
        return PreferenceDataSourceImpl(context)
    }

    /**
     * Repository Implementation
     * */
    @Singleton
    @Provides
    fun provideAuthenticationRepositoryImpl(
        authenticationRemoteDataSource: AuthenticationRemoteDataSource,
        accountLocalDataSource: AccountLocalDataSource,
        orderLocalDataSource: OrderLocalDataSource,
        notificationLocalDataSource: NotificationLocalDataSource,
        preferenceDataSource: PreferenceDataSource
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            authenticationRemoteDataSource,
            accountLocalDataSource,
            orderLocalDataSource,
            notificationLocalDataSource,
            preferenceDataSource
        )
    }

    @Singleton
    @Provides
    fun provideAccountRepositoryImpl(
        accountRemoteDataSource: AccountRemoteDataSource,
        accountLocalDataSource: AccountLocalDataSource,
        preferenceDataSource: PreferenceDataSource,
        roomDB: RoomDB
    ): AccountRepository {
        return AccountRepositoryImpl(
            accountRemoteDataSource,
            accountLocalDataSource,
            preferenceDataSource,
            roomDB
        )
    }

    @Singleton
    @Provides
    fun provideTicketRepositoryImpl(
        ticketRemoteDataSource: TicketRemoteDataSource,
        ticketLocalDataSource: TicketLocalDataSource,
        roomDB: RoomDB
    ) : TicketRepository {
        return TicketRepositoryImpl(
            ticketRemoteDataSource,
            ticketLocalDataSource,
            roomDB
        )
    }

    @Singleton
    @Provides
    fun provideOrderRepositoryImpl(
        orderRemoteDataSource: OrderRemoteDataSource,
        orderLocalDataSource: OrderLocalDataSource,
        preferenceDataSource: PreferenceDataSource,
        roomDB: RoomDB
    ) : OrderRepository {
        return OrderRepositoryImpl(
            orderRemoteDataSource,
            orderLocalDataSource,
            preferenceDataSource,
            roomDB
        )
    }

    @Singleton
    @Provides
    fun provideNotificationRepositoryImpl(
        notificationRemoteDataSource: NotificationRemoteDataSource,
        notificationLocalDataSource: NotificationLocalDataSource,
        preferenceDataSource: PreferenceDataSource,
        roomDB: RoomDB
    ) : NotificationRepository {
        return NotificationRepositoryImpl(
            notificationRemoteDataSource,
            notificationLocalDataSource,
            preferenceDataSource,
            roomDB
        )
    }
}