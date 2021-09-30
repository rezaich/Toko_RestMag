package com.zaich.toko_restmag.server

import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @POST("register")
    fun addUser(@Body newUserModel: UserModel) : Call<UserModel>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user_name")user_name:String,
              @Field("password")password:String
    ):Call<LoginResponse>
}