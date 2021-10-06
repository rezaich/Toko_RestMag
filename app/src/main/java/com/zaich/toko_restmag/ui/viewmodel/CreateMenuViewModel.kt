package com.zaich.toko_restmag.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.MenuModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class CreateMenuViewModel(application: Application) : AndroidViewModel(application) {
    private var serverInterface:ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private var createMenu  = MutableLiveData<MenuModel>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    fun setMenu(menuModel: MenuModel){
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
            serverInterface.storeProduct("Bearer "+ token,menuModel).enqueue(object : Callback<MenuModel> {
                override fun onResponse(call: Call<MenuModel>, response: Response<MenuModel>) {
                    Log.d("Menu","menu terkirim")

                    Toast.makeText(getApplication(), "menu terkirim", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(activity, LoginActivity::class.java))
                }

                override fun onFailure(call: Call<MenuModel>, t: Throwable) {
                    Log.d("logout",t.message.toString())
                }
            })
        }
    }
    fun getMenu():MutableLiveData<MenuModel> = createMenu
}