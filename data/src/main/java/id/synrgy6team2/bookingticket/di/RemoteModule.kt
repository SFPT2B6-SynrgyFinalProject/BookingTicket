package id.synrgy6team2.bookingticket.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.synrgy6team2.bookingticket.data.BuildConfig
import id.synrgy6team2.bookingticket.data.remote.service.AccountService
import id.synrgy6team2.bookingticket.data.remote.service.AuthenticationService
import id.synrgy6team2.bookingticket.data.remote.service.OrderService
import id.synrgy6team2.bookingticket.data.remote.service.TicketService
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.CookieManager
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().apply {
            setLenient()
            registerTypeAdapter(Date::class.java, JsonDeserializer { jsonElement, _, _ ->
                Date(jsonElement.asJsonPrimitive.asLong)
            })
        }.create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Singleton
    @Provides
    fun providerHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            followRedirects(false)
            retryOnConnectionFailure(true)
            addNetworkInterceptor(httpLoggingInterceptor)
            addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            cookieJar(JavaNetCookieJar(CookieManager()))
            connectTimeout(15, TimeUnit.MINUTES)
            writeTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideBookingRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder().apply {
            client(httpClient)
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
        }.build()
    }

    @Singleton
    @Provides
    fun provideAuthenticationApi(retrofit: Retrofit): AuthenticationService {
        return retrofit.create(AuthenticationService::class.java)
    }

    @Singleton
    @Provides
    fun provideAccountApi(retrofit: Retrofit): AccountService {
        return retrofit.create(AccountService::class.java)
    }

    @Singleton
    @Provides
    fun provideTicketApi(retrofit: Retrofit): TicketService {
        return retrofit.create(TicketService::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderApi(retrofit: Retrofit): OrderService {
        return retrofit.create(OrderService::class.java)
    }
}