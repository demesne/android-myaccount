package `in`.demesne.myaccount.features.auth

import `in`.demesne.myaccount.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthManager @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: TokenManager
) {

    suspend fun login(username: String, password: String): Result<String> {
        return try {
            val result = authRepository.authenticateWithOkta(username, password)

            if (result.isSuccess) {
                val tokenResponse = result.getOrNull()
                tokenResponse?.let { token ->
                    tokenManager.saveToken(token)
                    Result.success("Login successful")
                } ?: Result.failure(Exception("Invalid token response"))
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        tokenManager.clearToken()
    }
}