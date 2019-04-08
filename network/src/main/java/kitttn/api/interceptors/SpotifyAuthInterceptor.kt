package kitttn.api.interceptors

import kitttn.common.AppStorage
import okhttp3.Interceptor
import okhttp3.Response

class SpotifyAuthInterceptor(private val appStorage: AppStorage): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${appStorage.getAccessToken()}")
            .build()

        return chain.proceed(request)
    }
}