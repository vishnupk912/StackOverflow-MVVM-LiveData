package com.vishnu.stackoverlfow.data

import android.telecom.Call
import com.google.gson.annotations.SerializedName
import com.vishnu.stackoverlfow.data.modelrequest.PostmanRequest
import com.vishnu.stackoverlfow.data.modelresponse.PostmanResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("2.2/questions")
    fun getdata(
        @Query("key") key: String,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("site") site: String,
        @Query("tagged") tagged: String,


    ): retrofit2.Call<PostmanResponse>
}