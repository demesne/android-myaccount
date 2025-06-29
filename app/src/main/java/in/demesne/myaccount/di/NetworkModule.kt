package `in`.demesne.myaccount.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `in`.demesne.myaccount.data.api.AccountDataService
import `in`.demesne.myaccount.data.api.OktaApiService
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
    fun provideOktaApiService(@Named("okta") retrofit: Retrofit): OktaApiService {
        return retrofit.create(OktaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountDataService(@Named("okta") retrofit: Retrofit): AccountDataService {
        return retrofit.create(AccountDataService::class.java)
    }
}