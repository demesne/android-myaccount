package `in`.demesne.myaccount.data.models.account.webauthn

data class WebAuthnRegistrationResponse(
    val options: PublicKeyCredentialCreationOptions,
    val expiresAt: String?,
)

