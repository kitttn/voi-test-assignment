package kitttn.voiassignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kitttn.api.entities.Artist
import kitttn.api.services.SpotifyService
import retrofit2.HttpException
import retrofit2.await
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val spotifyService: SpotifyService) : CoroutineViewModel() {

    private val searchResultsLiveData by lazy {
        MutableLiveData<SearchResultsState>().apply { postValue(SearchResultsState.Initial) }
    }

    val searchResultsScreen: LiveData<SearchResultsState>
        get() = searchResultsLiveData

    fun requestArtists(query: String) = launch {
        searchResultsLiveData.postValue(SearchResultsState.Loading)
        Thread.sleep(2000)
        try {
            val response = spotifyService.getArtists(query).await()
            Log.i(TAG, "requestArtists: Loaded artsts: $response")
            searchResultsLiveData.postValue(SearchResultsState.Loaded(response.artists.items))
        } catch (e: HttpException) {
            Log.e(TAG, "requestArtists: Error while loading artists!")
            searchResultsLiveData.postValue(SearchResultsState.Error(e))
        }
    }

    companion object {
        private val TAG = SearchViewModel::class.java.simpleName
    }

    sealed class SearchResultsState {
        object Initial : SearchResultsState()
        data class Loaded(val items: List<Artist>) : SearchResultsState()
        data class Error(val error: Throwable) : SearchResultsState()
        object Loading : SearchResultsState()
    }
}