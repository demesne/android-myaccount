package `in`.demesne.myaccount.data.models.account.webauthn

data class WebAuthnRequest(
    val attestation: String,
    val clientData: String,
    val transports: String?,
    val clientExtensions: String?
)

