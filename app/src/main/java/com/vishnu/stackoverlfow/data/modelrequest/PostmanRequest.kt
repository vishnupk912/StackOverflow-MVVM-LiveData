package com.vishnu.stackoverlfow.data.modelrequest

import com.google.gson.annotations.SerializedName


class PostmanRequest (
        @SerializedName("key")
        val key: String,
        @SerializedName("order")
        val order: String,
        @SerializedName("sort")
        val sort: String,
        @SerializedName("site")
        val site: String
    )
