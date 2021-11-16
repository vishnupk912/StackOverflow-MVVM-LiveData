package com.vishnu.stackoverlfow.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.vishnu.stackoverlfow.data.interceptor.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiManger(appContext: Context) {
    var api: ApiServices

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val gson = GsonBuilder().serializeNulls().create()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(4000, TimeUnit.SECONDS)
            .readTimeout(4000, TimeUnit.SECONDS)
            .build()


        val BASE_URL="https://api.stackexchange.com/"
        val restAdapter = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        api = restAdapter.create(ApiServices::class.java)
    }


}