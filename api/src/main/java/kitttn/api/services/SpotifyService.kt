package kitttn.api.services

import kitttn.api.entities.Artist
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotifyService {
    @GET("/v1/search") // starting with slash to indicate absolute path - though it's not required by Retrofit
    fun getArtists(@Query("q") query: String): Deferred<List<Artist>>
}