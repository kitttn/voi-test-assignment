package kitttn.di

import dagger.Module
import dagger.Provides
import kitttn.api.interceptors.SpotifyAuthInterceptor
import kitttn.api.services.SpotifyService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ApiModule {
    @JvmStatic @Provides
    fun provideSpotifyService(): SpotifyService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addNetworkInterceptor(SpotifyAuthInterceptor())
            .addNetworkInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(SpotifyService::class.java)
    }
}