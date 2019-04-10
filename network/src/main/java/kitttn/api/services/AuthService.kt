package kitttn.api.services

import kitttn.api.entities.ExchangeTokenRequest
import kitttn.api.entities.TokenResponse
import kitttn.common.AppStorage.Companion.CLIENT_ID
import retrofit2.Call
import retrofit2.http.*

interface AuthService {
    @POST("/api/token")
    @FormUrlEncoded
    fun exchangeTokens(@Field("grant_type") grantType: String, @Field("code") code: String, @Field("redirect_uri") redirectUri: String): Call<TokenResponse>

    @POST("/api/token")
    @FormUrlEncoded
    fun refreshToken(@Field("refresh_code") refreshCode: String, @Field("grant_type") grantType: String = "refresh_token"): Call<TokenResponse>

    companion object {
        const val REDIRECT_URI = "voiapp://auth-result"
        const val BASE_URL = "https://accounts.spotify.com"

        const val AUTH_REQUEST = "$BASE_URL/authorize?client_id=$CLIENT_ID&response_type=code&redirect_uri=$REDIRECT_URI"
    }
}