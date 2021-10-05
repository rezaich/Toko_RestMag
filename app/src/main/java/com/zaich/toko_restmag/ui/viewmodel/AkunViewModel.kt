package com.zaich.toko_restmag.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.ui.activity.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AkunViewModel(application: Application) : AndroidViewModel(application)  {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val userLogout = MutableLiveData<JsonObject>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""


    fun setLogout(){
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref = getApplication<Application>().getSharedPreferences(
                "SharePref",
                Context.MODE_PRIVATE
            )
            token = sharedPref.getString("token", "")!!
            serverInterface.logout("Bearer " + token).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("logout pasti","logout")

                    Toast.makeText(getApplication(), "logout pasti", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(activity, LoginActivity::class.java))
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("logout",t.message.toString())
                }
            })
        }
    }

    fun getLogout():MutableLiveData<JsonObject> = userLogout
}