package kitttn.di

import dagger.Module
import dagger.Provides
import kitttn.api.interceptors.SpotifyAuthInterceptor
import kitttn.api.services.AuthService
import kitttn.api.services.SpotifyService
import kitttn.common.AppStorage
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ApiModule {
    @JvmStatic @Provides
    fun provideSpotifyService(appStorage: AppStorage): SpotifyService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(SpotifyAuthInterceptor(appStorage))
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(SpotifyService::class.java)
    }

    @JvmStatic @Provides
    fun provideAuthService(): AuthService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(AuthService::class.java)
    }
}