package kitttn.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import javax.inject.Inject

class AppStorage @Inject constructor(context: Context) {
    private val sharedPrefs by lazy { context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE) }

    fun getAccessToken() = sharedPrefs.getString(ACCESS_TOKEN_FIELD, "") ?: ""

    @SuppressLint("ApplySharedPref")
    fun saveTokens(accessToken: String, refreshToken: String, expiresIn: Int) {
        sharedPrefs.edit()
            .putString(ACCESS_TOKEN_FIELD, accessToken)
            .putString(REFRESH_TOKEN_FIELD, refreshToken)
            .putInt(EXPIRES_IN_TOKEN_FIELD, expiresIn)
            .commit()
    }

    /**
     * Creates base64(CLIENT + SECRET) signature
     */
    fun generateHeaderSignature(): String {
        val string = "$CLIENT_ID:$CLIENT_SECRET".toByteArray()
        return Base64.encodeToString(string, 0).replace("\n", "")
    }

    companion object {
        const val SHARED_PREFS_NAME = "voiSharedPrefs"
        private const val ACCESS_TOKEN_FIELD = "accToken"
        private const val REFRESH_TOKEN_FIELD = "refreshToken"
        private const val EXPIRES_IN_TOKEN_FIELD = "expiresInToken"

        const val CLIENT_ID = "9b9c517c083b4f1dbf0ff71d9dc0c7cc"
        const val CLIENT_SECRET = "ba5e03d8f11b4aec8fb76f1487b696d0"
    }
}