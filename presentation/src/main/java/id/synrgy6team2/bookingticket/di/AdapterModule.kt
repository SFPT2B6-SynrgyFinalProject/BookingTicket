package id.synrgy6team2.bookingticket.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import id.synrgy6team2.bookingticket.presentation.searchticket.AirportAdapter
import id.synrgy6team2.bookingticket.presentation.searchticket.TypeFlightAdapter

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {
    @Provides
    fun provideSearchAirportAdapter(): AirportAdapter {
        return AirportAdapter()
    }

    @Provides
    fun provideTypeFlightAdapter(): TypeFlightAdapter {
        return TypeFlightAdapter()
    }
}