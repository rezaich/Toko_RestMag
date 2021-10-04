package com.zaich.toko_restmag.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
//    var BASE_URL : String = "http://rezaich.teknisitik.com/api/v1/"
    val BASE_URL = "http://10.0.2.2:8000/api/v1/"
    var retrofit : Retrofit? = null

    fun getApiClient():Retrofit?{
        if(retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return  retrofit
    }
}