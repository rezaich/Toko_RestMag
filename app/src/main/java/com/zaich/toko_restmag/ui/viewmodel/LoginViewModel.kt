package com.zaich.toko_restmag.ui.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel:ViewModel() {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val userLogin = MutableLiveData<LoginResponse>()

    fun setLogin(user_name : String , password : String){
        serverInterface.login(user_name, password).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    Log.d(TAG, response.body()?.token.toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getLogin():MutableLiveData<LoginResponse> = userLogin

    companion object{
         const val TAG  = "LOGIN"
    }
}