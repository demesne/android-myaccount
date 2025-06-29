package `in`.demesne.myaccount.data.api

import `in`.demesne.myaccount.data.models.login.OktaTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApiService {
    @FormUrlEncoded
    @POST("oauth2/v1/token")
    suspend fun getToken(
        @Header("Authorization") basicAuth: String,
        @Field("grant_type") grantType: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String
    ): Response<OktaTokenResponse>
}