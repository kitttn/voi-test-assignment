package kitttn.voiassignment.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import kitttn.api.services.SpotifyService
import kitttn.voiassignment.R
import kitttn.voiassignment.extensions.component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : FragmentActivity(), CoroutineScope {
    @Inject lateinit var spotifyService: SpotifyService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        loadData()
    }

    private fun loadData() {
        launch(Dispatchers.IO) {
            try {
                val artists = spotifyService.getArtists("Imagine").await()
                Log.i(TAG, "loadData: Loaded artists: $artists")
            } catch (e: Exception) {
                Log.i(TAG, "loadData: Got error: $e")
            }
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}