package com.vishnu.stackoverlfow.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.vishnu.stackoverlfow.data.modelresponse.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.content.ContextCompat.getSystemService




fun <T : Any> Call<T>.awaitResponse(
    onSuccess: (T?) -> Unit = {},
    onFailure: (String?) -> Unit = {}
) {

    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val r = response
            if (response.isSuccessful) {
                onSuccess.invoke(response.body())
            } else {
                val gson = Gson()
                val (error, message, status) = gson.fromJson(
                    response.errorBody()!!.charStream(),
                    BaseResponse::class.java
                ).also {
                    onFailure.invoke(it.message)
                }
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            onFailure.invoke(t.message)
        }
    })
}

fun dateFormat(dateStr: String): String {
    var format=""
    try {
        val sdfInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val date = sdfInput.parse(dateStr)
        val sdfOutput = SimpleDateFormat("dd-MM-yyyy")
        sdfOutput.timeZone = TimeZone.getTimeZone("Etc/UTC")
        format = sdfOutput.format(date)

    }catch (e:Exception){

    }
    return format


}

public fun hasInternet(context: Context):Boolean{
    val connectivityManager = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return when{
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false

    }
}
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!
        .isConnected

}
