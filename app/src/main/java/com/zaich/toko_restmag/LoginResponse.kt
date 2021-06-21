package com.zaich.toko_restmag

import com.google.gson.annotations.SerializedName

class LoginResponse (@SerializedName("token") val token : String?){
}