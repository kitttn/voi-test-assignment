package kitttn.voiassignment.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

open class CoroutineViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel(), CoroutineScope {
    private var coroutines: CoroutineContext = dispatcher
    final override val coroutineContext: CoroutineContext
        get() = coroutines

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        coroutines += launch(dispatcher, CoroutineStart.DEFAULT, block)
    }

    final override fun onCleared() {
        super.onCleared()
        coroutines.cancel()
    }
}