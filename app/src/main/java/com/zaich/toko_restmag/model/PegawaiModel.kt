package com.zaich.toko_restmag.model

import com.google.gson.annotations.SerializedName

data class PegawaiModel (
    val name : String,
    val user_name : String,
    @SerializedName("password") val phone:String
        )