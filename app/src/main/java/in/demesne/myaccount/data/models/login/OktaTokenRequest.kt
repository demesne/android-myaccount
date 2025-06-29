package `in`.demesne.myaccount.data.models.login

data class OktaTokenRequest(
    val grant_type: String = "password",
    val username: String,
    val password: String,
    val scope: String = "openid profile email"
)