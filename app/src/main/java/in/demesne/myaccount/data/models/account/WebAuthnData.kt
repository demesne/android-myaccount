package `in`.demesne.myaccount.data.models.account

data class WebAuthnData(
    val id: String,
    val status: String,
    val type: String,
    val key: String,
    val name: String,
    val credentialId: String,
    val created: String,
    val lastUpdated: String,
)