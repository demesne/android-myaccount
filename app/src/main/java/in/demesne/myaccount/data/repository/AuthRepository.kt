package `in`.demesne.myaccount.data.repository

import android.util.Base64
import `in`.demesne.myaccount.data.api.OktaApiService
import `in`.demesne.myaccount.data.models.login.OktaTokenResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val oktaApiService: OktaApiService
) {
    companion object {
        private const val CLIENT_ID = "0oafkco4d2UrtLrVI0w6"
        private const val CLIENT_SECRET = "v_sbvSncNNzwpVcKD4SsHbCYe0assag0_WlPvOtJBQS98qXgcWBw50RLeS2in9sq"
    }

    suspend fun authenticateWithOkta(username: String, password: String): Result<OktaTokenResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val credentials = "$CLIENT_ID:$CLIENT_SECRET"
                val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

                val response = oktaApiService.getToken(
                    basicAuth = basicAuth,
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