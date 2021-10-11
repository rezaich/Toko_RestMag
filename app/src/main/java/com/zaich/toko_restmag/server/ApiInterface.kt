package com.zaich.toko_restmag.server

import com.google.gson.JsonObject
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.model.LogoutResponse
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.model.PegawaiModel
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.model.DetailUserModel
import com.zaich.toko_restmag.model.ServerResponse
import okhttp3.MultipartBody
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

    @POST("products/store")
    fun storeProduct(@Header("Authorization")authHeader: String,
                   @Body createMenu : MenuModel
    ):Call<MenuModel>

//    @Multipart
//    @POST("uploads/upload_image.php")
//    fun uploadImage(@Header("Authorization")authHeader: String,
//    @Part imagename: MultipartBody.Part):Call<ServerResponse>

    @GET("products")
    fun showProduct(@Header("Authorization")authHeader: String):Call<JsonObject>

    @POST("store")
    fun storeUser(@Header("Authorization")authHeader: String,
    @Body CreateDetailUser : DetailUserModel ) : Call<LogoutResponse>
}