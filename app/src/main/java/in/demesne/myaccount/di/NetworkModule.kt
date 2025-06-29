package `in`.demesne.myaccount.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.demesne.myaccount.data.api.MyAccountDataService
import `in`.demesne.myaccount.data.api.LoginApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    @Named("okta")
    fun provideOktaRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://oie.tc2.okta.demesne.in/") // Replace with your Okta domain
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOktaApiService(@Named("okta") retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountDataService(@Named("okta") retrofit: Retrofit): MyAccountDataService {
        return retrofit.create(MyAccountDataService::class.java)
    }
}