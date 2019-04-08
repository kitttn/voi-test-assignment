package kitttn.api.services

import kitttn.api.entities.ExchangeTokenRequest
import kitttn.api.entities.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @GET("/authorize")
    fun authorize(@Query("client_id") clientId: String = CLIENT_ID,
                  @Query("response_type") responseType: String = "code",
                  @Query("redirect_uri") redirectUri: String = REDIRECT_URI): Call<Unit>

    @POST("/api/token")
    fun exchangeTokens(@Body exchangeToken: ExchangeTokenRequest): Call<TokenResponse>

    companion object {
        const val CLIENT_ID = "9b9c517c083b4f1dbf0ff71d9dc0c7cc"
        const val CLIENT_SECRET = "ba5e03d8f11b4aec8fb76f1487b696d0"
        const val REDIRECT_URI = "voiapp://auth-result"
    }
}