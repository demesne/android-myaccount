package `in`.demesne.myaccount.data.models.login

data class OktaTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val refresh_token: String?,
    val scope: String
)