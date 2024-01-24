package id.synrgy6team2.bookingticket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.domain.repository.AccountRepository
import id.synrgy6team2.bookingticket.domain.repository.AccountUseCase
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationRepository
import id.synrgy6team2.bookingticket.domain.repository.AuthenticationUseCase
import id.synrgy6team2.bookingticket.domain.repository.TicketRepository
import id.synrgy6team2.bookingticket.domain.repository.TicketUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Singleton
    @Provides
    fun provideAuthenticationUseCase(authenticationRepository: AuthenticationRepository): AuthenticationUseCase {
        return AuthenticationUseCase(authenticationRepository)
    }

    @Singleton
    @Provides
    fun provideAccountUseCase(accountRepository: AccountRepository): AccountUseCase {
        return AccountUseCase(accountRepository)
    }

    @Singleton
    @Provides
    fun provideTicketUseCase(ticketRepository: TicketRepository): TicketUseCase {
        return TicketUseCase(ticketRepository)
    }
}