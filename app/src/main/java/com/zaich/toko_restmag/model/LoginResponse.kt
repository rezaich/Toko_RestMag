package com.zaich.toko_restmag.model

import com.google.gson.annotations.SerializedName

class LoginResponse (
    @SerializedName("token") val token : String?
    )