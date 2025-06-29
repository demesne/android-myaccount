package `in`.demesne.myaccount.data.api

import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnData
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRequest
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AccountDataService {
    @GET("/idp/myaccount/webauthn")
    suspend fun getAccountData(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = "application/json; okta-version=1.0.0"
    ): Response<List<WebAuthnData>>

    @POST("/idp/myaccount/webauthn/registration")
    suspend fun startPasskeyEnrollment(
        @Header("Authorization") token: String,
        @Header("Accept") accept: String = "application/json; okta-version=1.0.0"
    ): Response<WebAuthnRegistrationResponse>

    @POST("/idp/myaccount/webauthn")
    suspend fun completePasskeyEnrollment(
        @Header("Authorization") token: String,
        @Body credential: WebAuthnRequest,
        @Header("Accept") accept: String = "application/json; okta-version=1.0.0"
    ): Response<WebAuthnData>
}