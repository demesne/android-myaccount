package `in`.demesne.myaccount.data.models.login

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String? = null
)