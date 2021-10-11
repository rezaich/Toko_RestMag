package com.zaich.toko_restmag.model

import com.google.gson.annotations.SerializedName

class ServerResponse {

    @SerializedName("success")
    var success = false

    @SerializedName("message")
    var message: String? = null

    @JvmName("getMessage1")
    fun getMessage(): String? {
        return message
    }
    @JvmName("getSuccess1")
    fun getSuccess(): Boolean {
        return success
    }
}