package com.vishnu.stackoverlfow.data.interceptor

import android.content.Context
import com.vishnu.stackoverlfow.utils.NoInternetException
import com.vishnu.stackoverlfow.utils.hasInternet
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private  val context :Context):Interceptor{

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!hasInternet(context)) {
            throw NoInternetException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}