package `in`.demesne.myaccount.features.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRegistrationResponse
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRequest
import `in`.demesne.myaccount.data.repository.AccountRepository
import `in`.demesne.myaccount.features.auth.AuthManager
import `in`.demesne.myaccount.features.auth.TokenManager
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val authManager: AuthManager,
    private val tokenManager: TokenManager
) : ViewModel() {

    private val _accountState = MutableLiveData<MyAccountState>()
    val accountState: LiveData<MyAccountState> = _accountState

    private val _logoutEvent = MutableLiveData<Boolean>()
    val logoutEvent: LiveData<Boolean> = _logoutEvent

    init {
        loadAccountData()
    }

    private fun loadAccountData() {
        _accountState.value = MyAccountState.Loading

        viewModelScope.launch {
            val token = tokenManager.getAccessToken()
            if (token != null) {
                val result = accountRepository.getAccountData(token)

                if (result.isSuccess) {
                    _accountState.value = MyAccountState.Success(result.getOrNull() ?: emptyList())
                } else {
                    _accountState.value = MyAccountState.Error(
                        result.exceptionOrNull()?.message ?: "Failed to load data"
                    )
                }
            } else {
                _accountState.value = MyAccountState.Error("No valid token found")
            }
        }
    }

    fun logout() {
        authManager.logout()
        _logoutEvent.value = true
    }

    fun refreshData() {
        loadAccountData()
    }

    fun startPasskeyEnrollment(activity: android.app.Activity) {
        viewModelScope.launch {
            _accountState.value = MyAccountState.Loading
            val token = tokenManager.getAccessToken()
            if (token == null) {
                _accountState.value = MyAccountState.Error("No valid token found")
                return@launch
            }
            // 1. Start enrollment with Okta
            val optionsResult = accountRepository.startPasskeyEnrollment(token)
            if (optionsResult.isFailure) {
                _accountState.value = MyAccountState.Error(optionsResult.exceptionOrNull()?.message ?: "Failed to start passkey enrollment")
                return@launch
            }
            val options = optionsResult.getOrNull()!!

            // 2. Call Android Passkey API (placeholder)
            // TODO: Replace with actual Android Passkey API integration
            val credential = createPasskeyWithAndroidAPI(activity, options)
            if (credential == null) {
                _accountState.value = MyAccountState.Error("Passkey creation cancelled or failed")
                return@launch
            }

            // 3. Complete enrollment with Okta
            val completeResult = accountRepository.completePasskeyEnrollment(token, credential)
            if (completeResult.isFailure) {
                _accountState.value = MyAccountState.Error(completeResult.exceptionOrNull()?.message ?: "Failed to complete passkey enrollment")
                return@launch
            }

            // 4. Refresh the list
            loadAccountData()
        }
    }

    // Placeholder for Android Passkey API integration
    private fun createPasskeyWithAndroidAPI(activity: android.app.Activity, options: WebAuthnRegistrationResponse): WebAuthnRequest? {
        suspend fun createPasskey(
            activity: Activity,
            optionsJson: String
        ): CreatePublicKeyCredentialResponse? {
            val credentialManager = CredentialManager.create(activity)
            val request = CreatePublicKeyCredentialRequest(optionsJson)
            return try {
                credentialManager.createCredential(
                    activity,
                    request
                ) as CreatePublicKeyCredentialResponse
            } catch (e: CreateCredentialException) {
                Log.e("MyAccountRepository", "CreateCredentialException: ${e.errorMessage}", e)
                throw e
            } catch (e: Exception) {
                Log.e("MyAccountRepository", "Unexpected exception: ${e.message}", e)
                throw e
            }
        }

    }
}