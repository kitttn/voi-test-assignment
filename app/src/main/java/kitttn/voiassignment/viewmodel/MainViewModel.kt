package kitttn.voiassignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kitttn.api.entities.ExchangeTokenRequest
import kitttn.api.services.AuthService
import kitttn.common.AppStorage
import retrofit2.await
import java.lang.Exception
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val authService: AuthService,
    private val appStorage: AppStorage) : CoroutineViewModel() {

    private val screenLiveData = MutableLiveData<MainScreenState>()
    val screenState: LiveData<MainScreenState>
        get() = screenLiveData

    fun checkAuthenticationExists(): Boolean = appStorage.getRefreshToken().isNotEmpty()

    fun authenticate(code: String) = launch {
        screenLiveData.postValue(MainScreenState.Loading)

        try {
            val token = authService
                .exchangeTokens(ExchangeTokenRequest.GRANT_TYPE_AUTH_CODE, code, AuthService.REDIRECT_URI)
                .await()

            appStorage.saveTokens(token.accessToken, token.refreshToken, token.expiresIn)
            screenLiveData.postValue(MainScreenState.SearchReady(""))
        } catch (e: Exception) {
            screenLiveData.postValue(MainScreenState.Error(e))
        }
    }
}

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Error(val error: Throwable) : MainScreenState()
    data class SearchReady(val searchQuery: String) : MainScreenState()
}