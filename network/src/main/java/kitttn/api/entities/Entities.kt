package kitttn.api.entities

import com.google.gson.annotations.SerializedName

data class Artist(val genres: List<String>,
                  val id: String,
                  val images: List<Image>,
                  val name: String,
                  val uri: String)

data class Paged<T>(val items: List<T>)

data class ArtistsResp(val artists: Paged<Artist>)

data class Image(val width: Int, val height: Int, val url: String)

data class ExchangeTokenRequest(@SerializedName("grant_type") val grantType: String,
                                val code: String,
                                @SerializedName("redirect_uri") val redirectUri: String? = null) {
    companion object {
        const val GRANT_TYPE_AUTH_CODE = "authorization_code"
        const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }
}

data class TokenResponse(@SerializedName("access_token") val accessToken: String,
                         @SerializedName("expires_in") val expiresIn: Int = 3600,
                         @SerializedName("refresh_token") val refreshToken: String)