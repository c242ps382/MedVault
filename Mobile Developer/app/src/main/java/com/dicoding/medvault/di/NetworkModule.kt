package com.dicoding.medvault.di

import android.content.Context
import com.dicoding.medvault.data.network.ApiService
import com.dicoding.medvault.data.network.PredictService
import com.dicoding.medvault.data.network.RetrofitClient
import com.dicoding.medvault.pref.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttp(@ApplicationContext context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val modifiedRequest = originalRequest.newBuilder()
                val appPreferences = AppPreferences(context)
                val tempUser = appPreferences.getUser()
                if (tempUser != null && tempUser.token.isNotBlank()) {
                    modifiedRequest.addHeader("Authorization", "Bearer ${tempUser.token}")
                }
                modifiedRequest.addHeader("Accept", "application/json")
                val finalRequest = modifiedRequest.build()
                chain.proceed(finalRequest)
            }
            .build()
    }

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun providePredictService(): PredictService {
        return Retrofit.Builder()
            .baseUrl("https://ml-923957567288.asia-southeast2.run.app/")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PredictService::class.java)
    }
}