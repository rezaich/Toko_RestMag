package com.zaich.toko_restmag.server

import com.google.gson.JsonObject
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.model.LogoutResponse
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.model.PegawaiModel
import com.zaich.toko_restmag.model.MenuModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("register")
    fun addUser(@Body newUserModel: UserModel) : Call<UserModel>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user_name")user_name:String,
              @Field("password")password:String
    ):Call<LoginResponse>

    @POST("logout")
    fun logout(@Header("Authorization")authHeader: String): Call<JsonObject>

    @POST("registers")
    fun createUser(@Header("Authorization")authHeader: String,
                   @Body createPegawaiModel : PegawaiModel
    ):Call<PegawaiModel>

    @POST("/products/store")
    fun storeProduct(@Header("Authorization")authHeader: String,
                   @Body createMenu : MenuModel
    ):Call<MenuModel>
}