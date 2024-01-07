package id.synrgy6team2.bookingticket.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSource
import id.synrgy6team2.bookingticket.data.local.datasource.PreferenceDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationDataSource
import id.synrgy6team2.bookingticket.data.remote.datasource.AuthenticationDataSourceImpl
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import id.synrgy6team2.bookingticket.data.repository.AuthenticationRepositoryImpl
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideAuthenticationDataSourceImpl(
        authenticationService: AuthenticationService
    ): AuthenticationDataSource {
        return AuthenticationDataSourceImpl(authenticationService)
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

    @Singleton
    @Provides
    fun provideAuthenticationRepositoryImpl(
        authenticationDataSource: AuthenticationDataSource,
        preferenceDataSource: PreferenceDataSource
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationDataSource, preferenceDataSource)
    }
}