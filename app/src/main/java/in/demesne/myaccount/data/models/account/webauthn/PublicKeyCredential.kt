package `in`.demesne.myaccount.data.models.account.webauthn

data class PublicKeyCredential(
    val id: String,
    val rawId: String,
    val type: String,
    val response: AuthenticatorAttestationResponse,
    val clientExtensionResults: Map<String, Any>?,
    val transports: List<String>?
)

data class AuthenticatorAttestationResponse(
    val attestationObject: String,
    val clientDataJSON: String
)