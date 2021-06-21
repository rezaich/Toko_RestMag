package com.zaich.toko_restmag

import com.zaich.toko_restmag.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface Apinterface {

    @POST("register")
    fun addUser(@Body newUserModel: UserModel) : Call<UserModel>

    @POST("login")
    fun login(@Field("user_name") user_name:String,
              @Field("password") password:String
    ):Call<LoginResponse>
}