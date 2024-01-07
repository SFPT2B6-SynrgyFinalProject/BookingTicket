package id.synrgy6team2.bookingticket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideAuthenticationUseCase(authenticationRepository: AuthenticationRepository):AuthenticationUseCase {
        return AuthenticationUseCase(authenticationRepository)
    }
}