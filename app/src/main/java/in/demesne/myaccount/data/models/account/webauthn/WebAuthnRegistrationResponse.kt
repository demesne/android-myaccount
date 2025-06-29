package `in`.demesne.myaccount.data.models.account.webauthn

data class WebAuthnRegistrationResponse(
    val options: PublicKeyCredentialCreationOptions,
    val expiresAt: String?,
)

data class PublicKeyCredentialCreationOptions(
    val rp: RpEntity,
    val user: UserEntity,
    val pubKeyCredParams: List<PubKeyCredParam>,
    val timeout: Long?,
    var challenge: String,
    val attestation: String?,
    val authenticatorSelection: AuthenticatorSelection?,
    val u2fParams: U2fParams?,
    val excludeCredentials: List<ExcludeCredential>?,
    val extensions: Extensions?
)

data class RpEntity(
    val name: String, var id: String?
)

data class UserEntity(
    val displayName: String, val name: String, val id: String
)

data class PubKeyCredParam(
    val type: String, val alg: Int
)

data class AuthenticatorSelection(
    val userVerification: String?, val requireResidentKey: Boolean?, val residentKey: String?
)

data class U2fParams(
    val appid: String?
)

data class ExcludeCredential(
    val type: String, val id: String
)

data class Extensions(
    val credProps: Boolean?
)
