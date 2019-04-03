package kitttn.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class SpotifyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", OAUTH_TOKEN)
            .build()

        return chain.proceed(request)
    }

    // TODO: replace with Spotify Auth
    companion object {
        const val OAUTH_TOKEN = "Bearer BQAwl6V0HxEiniQZ3TyF8M_stMgAmKV1UGYGm2tLfGJ8TSNAnDk3k09TpbWuxXMQmEXV6KyOdGGm6EvmVrTEwla5ZrmPZwAnXKka8-DZdFDhRDCSdxCrXoj-05mfKfs54sQIZxHMDGrQsTg1GpGpQvraBK6pdfg4FQ"
    }
}