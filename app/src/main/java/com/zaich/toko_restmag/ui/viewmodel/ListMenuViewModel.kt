package com.zaich.toko_restmag.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.model.MenuModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMenuViewModel(application: Application) : AndroidViewModel(application) {
    private val serverInterface: ApiInterface =
        ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val listMenu = MutableLiveData<ArrayList<MenuModel>>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    fun setList() {
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
            serverInterface.showProduct("Bearer " + token).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("menu", "List Menu")

                val myJson = response.body()
                val myData = myJson!!.getAsJsonArray("data")

                val arrayItem = ArrayList<MenuModel>()
                for (i in 0 until myData.size()){
                    var myRecord = myData.get(i).asJsonObject
                    var price = myRecord.get("price").asInt
                    var name = myRecord.get("name").asString
                    var desc = myRecord.get("description").asString
                    var image = myRecord.get("image_link").asString
                    var categoryid = myRecord.get("category_id").asInt

                    Log.d("Log "+i.toString(), myData.get(i).toString())
                    arrayItem.add(MenuModel( name,price,desc,image,categoryid))
                }
                Log.d("Array Item", arrayItem.toString())
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
        }
    }

    fun getList():MutableLiveData<ArrayList<MenuModel>> = listMenu
}