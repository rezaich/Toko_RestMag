package com.zaich.toko_restmag.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterMainViewModel:ViewModel() {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val userRegist = MutableLiveData<UserModel>()

    fun setRegist(userModel: UserModel){
        serverInterface.addUser(userModel).enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) userRegist.postValue(response.body())
                Log.d("Regist","Register successfully")
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }
        })
    }

    fun getRegist():MutableLiveData<UserModel> = userRegist
}