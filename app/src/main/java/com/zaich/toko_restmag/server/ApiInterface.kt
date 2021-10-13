package com.zaich.toko_restmag.server

import com.google.gson.JsonObject
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.model.LogoutResponse
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.model.PegawaiModel
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.model.DetailUserModel
import com.zaich.toko_restmag.model.ServerResponse
import com.zaich.toko_restmag.pegawai.model.DefaultResponse
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

    @GET("products")
    fun showProduct(@Header("Authorization")authHeader: String):Call<JsonObject>

    @POST("update")
    fun storeUser(@Header("Authorization")authHeader: String,
    @Body CreateDetailUser : DetailUserModel ) : Call<LogoutResponse>

    @GET("users")
    fun showUsers(@Header("Authorization")authHeader: String):Call<JsonObject>

    @GET("show")
    fun showDetail(@Header("Authorization")authHeader: String):Call<JsonObject>

    /** API PEMSESANAN*/
    @GET("category")
    fun getCategories(@Header("Authorization") authHeader:String)
            : Call<JsonObject>

    @FormUrlEncoded
    @POST("carts")
    fun addProductToCart(@Header("Authorization") authHeader: String,
                         @Field("product_id") productId: Int,
                         @Field("price") price: Int,
                         @Field("quantity") quantity: Int,
                         @Field("daydate") daydate: String,
                         @Field("daytime") daytime: String) : Call<DefaultResponse>
    @FormUrlEncoded
    @POST("transactions")
    fun addTransaction(@Header("Authorization") authHeader: String,
                       @Field("total") total: Int,
                       @Field("deposit_time") deposit_time: String?,
                       @Field("deposit_date") deposit_date: String?) : Call<DefaultResponse>

    @GET("products/searchByCategory/{categoryId}")
    fun getProductsByCategory(@Header("Authorization") authHeader:String,
                              @Path("categoryId") id: Int): Call<JsonObject>

    @GET("carts/showByUser")
    fun getCartsByUser(@Header("Authorization") authHeader: String): Call<JsonObject>
}