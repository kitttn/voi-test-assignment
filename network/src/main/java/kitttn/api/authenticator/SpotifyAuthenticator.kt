package kitttn.api.authenticator

import android.util.Log
import kitttn.api.services.AuthService
import kitttn.common.AppStorage
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.lang.Exception

class SpotifyAuthenticator(private val appStorage: AppStorage,
                           private val authService: AuthService) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return try {
            val authResponse = authService.refreshToken(appStorage.getRefreshToken()).execute()
            if (authResponse.isSuccessful) {
                val tokenResp = authResponse.body() ?: return null
                appStorage.saveTokens(tokenResp.accessToken, tokenResp.refreshToken, tokenResp.expiresIn)
                response.request()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "authenticate: Can't exchange tokens!", e)
            null
        }
    }

    companion object {
        private val TAG = SpotifyAuthenticator::class.java.simpleName
    }
}