package id.synrgy6team2.bookingticket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import id.synrgy6team2.bookingticket.presentation.dashboard.DashboardHistoryAdapter
import id.synrgy6team2.bookingticket.presentation.dashboard.DashboardMainMenuAdapter
import id.synrgy6team2.bookingticket.presentation.dashboard.DashboardPopularAdapter
import id.synrgy6team2.bookingticket.presentation.dashboard.DashboardRecomendedAdapter
import id.synrgy6team2.bookingticket.presentation.notification.NotificationAdapter
import id.synrgy6team2.bookingticket.presentation.profile.ProfileAdapter

@Module
@InstallIn(FragmentComponent::class)
object AdapterModule {
    @Provides
    fun provideDashboardMainMenuAdapter(): DashboardMainMenuAdapter {
        return DashboardMainMenuAdapter()
    }

    @Provides
    fun provideDashboardRecomendedAdapter(): DashboardRecomendedAdapter {
        return DashboardRecomendedAdapter()
    }

    @Provides
    fun provideDashboardHistoryAdapter(): DashboardHistoryAdapter {
        return DashboardHistoryAdapter()
    }

    @Provides
    fun provideDashboardPopularAdapter(): DashboardPopularAdapter {
        return DashboardPopularAdapter()
    }

    @Provides
    fun provideProfileAdapter(): ProfileAdapter {
        return ProfileAdapter()
    }

    @Provides
    fun provideNotificationAdapter(): NotificationAdapter {
        return NotificationAdapter()
    }
}