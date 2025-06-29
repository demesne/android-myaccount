package `in`.demesne.myaccount.features.auth

import android.content.Context
import android.content.SharedPreferences
import `in`.demesne.myaccount.data.models.login.OktaTokenResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "myaccount_prefs"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_TOKEN_TYPE = "token_type"
        private const val KEY_EXPIRES_IN = "expires_in"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(tokenResponse: OktaTokenResponse) {
        prefs.edit().apply {
            putString(KEY_ACCESS_TOKEN, tokenResponse.access_token)
            putString(KEY_REFRESH_TOKEN, tokenResponse.refresh_token)
            putString(KEY_TOKEN_TYPE, tokenResponse.token_type)
            putInt(KEY_EXPIRES_IN, tokenResponse.expires_in)
            apply()
        }
    }

    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun isLoggedIn(): Boolean {
        return getAccessToken() != null
    }

    fun clearToken() {
        prefs.edit().clear().apply()
    }
}