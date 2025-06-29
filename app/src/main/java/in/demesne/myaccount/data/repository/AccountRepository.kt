package `in`.demesne.myaccount.data.repository

import `in`.demesne.myaccount.data.api.MyAccountDataService
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnData
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRequest
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRegistrationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val myAccountDataService: MyAccountDataService
) {

    suspend fun getAccountData(token: String): Result<List<WebAuthnData>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = myAccountDataService.getAccountData("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to fetch data: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun startPasskeyEnrollment(token: String): Result<WebAuthnRegistrationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = myAccountDataService.startPasskeyEnrollment("Bearer $token")
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Failed to start passkey enrollment: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun completePasskeyEnrollment(token: String, credential: WebAuthnRequest): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = myAccountDataService.completePasskeyEnrollment("Bearer $token", credential)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to complete passkey enrollment: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    suspend fun deleteWebAuthn(token: String, id: String): Result<Unit> {
        return withContext(Dispatchers.IO) {
            try {
                val response = myAccountDataService.deleteWebAuthn("Bearer $token", id)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception("Failed to delete credential: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}