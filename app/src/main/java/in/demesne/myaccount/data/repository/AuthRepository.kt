package `in`.demesne.myaccount.data.repository

import android.util.Base64
import `in`.demesne.myaccount.data.api.LoginApiService
import `in`.demesne.myaccount.data.models.login.OktaTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val loginApiService: LoginApiService
) {
    companion object {
        private const val CLIENT_ID = "0oafkco4d2UrtLrVI0w6"
    }

    suspend fun authenticateWithOkta(username: String, password: String): Result<OktaTokenResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = loginApiService.getToken(
                    clientId = CLIENT_ID,
                    grantType = "password",
                    username = username,
                    password = password,
                    scope = "openid profile email okta.myAccount.webauthn.read okta.myAccount.webauthn.manage"
                )

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Authentication failed: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}