package id.synrgy6team2.bookingticket.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.data.local.dao.AccountDao
import id.synrgy6team2.bookingticket.data.local.database.RoomDB
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.AccountLocalDataSourceImpl
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.AccountRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.AccountRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationRemoteDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationRemoteDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.service.AccountService
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import id.synrgy6team2.bookingticket.data.repository.AccountRepositoryImpl
import id.synrgy6team2.bookingticket.data.repository.AuthenticationRepositoryImpl
import id.synrgy6team2.bookingticket.domain.repository.AccountRepository
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
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
    fun provideAccountLocalDataSourceImpl(
        accountDao: AccountDao
    ): AccountLocalDataSource {
        return AccountLocalDataSourceImpl(accountDao)
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
        preferenceDataSource: PreferenceDataSource
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            authenticationRemoteDataSource,
            accountLocalDataSource,
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
}