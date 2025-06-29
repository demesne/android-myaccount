package `in`.demesne.myaccount.data.models.account.webauthn

data class WebAuthnRequest(
    val id: String,
    val rawId: String,
    val type: String,
    val response: Map<String, String>,
    val clientExtensionResults: Map<String, Any>? = null
)

