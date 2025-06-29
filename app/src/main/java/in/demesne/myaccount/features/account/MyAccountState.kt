package `in`.demesne.myaccount.features.account

import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnData

sealed class MyAccountState {
    object Loading : MyAccountState()
    data class Success(val data: List<WebAuthnData>) : MyAccountState()
    data class Error(val message: String) : MyAccountState()
}