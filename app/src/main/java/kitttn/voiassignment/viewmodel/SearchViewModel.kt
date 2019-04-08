package kitttn.voiassignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kitttn.api.services.SpotifyService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchViewModel @Inject constructor(private val spotifyService: SpotifyService) : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    fun requestArtists(query: String) = launch {
        val artists = spotifyService.getArtists(query).await()
        Log.i(TAG, "requestArtists: Loaded artsts: $artists")
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }
}