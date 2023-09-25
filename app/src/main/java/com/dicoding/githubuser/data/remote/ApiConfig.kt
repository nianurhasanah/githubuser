package com.dicoding.githubuser.data.remote

import com.dicoding.githubuser.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object ApiConfig {
    private val okHttp = OkHttpClient.Builder()
        .apply {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(loggingInterceptor)
        }
        .readTimeout(25,TimeUnit.SECONDS)
        .writeTimeout(300,TimeUnit.SECONDS)
        .connectTimeout(60,TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.URL_API)
        .client(okHttp)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userGitService = retrofit.create<ApiService>()

}