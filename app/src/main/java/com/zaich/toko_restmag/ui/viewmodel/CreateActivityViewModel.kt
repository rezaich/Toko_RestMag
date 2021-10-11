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
import com.google.gson.JsonObject
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.DetailUserModel
import retrofit2.Callback
import com.zaich.toko_restmag.model.LogoutResponse
import retrofit2.Call
import retrofit2.Response

class CreateActivityViewModel (application: Application) : AndroidViewModel(application)  {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val detail = MutableLiveData<DetailUserModel>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    fun setDetail(detailUserModel: DetailUserModel) {
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

            serverInterface.storeUser("Bearer " + token, detailUserModel)
                .enqueue(object : Callback<LogoutResponse> {
                    override fun onResponse(
                        call: Call<LogoutResponse>,
                        response: Response<LogoutResponse>,
                    ) {
                        Log.d("Detail", "Detail terkirim")

                        Toast.makeText(getApplication(), "detail terkirim", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                        Log.d("Detail", t.message.toString())
                    }
                })
        }
        fun getdetail():MutableLiveData<DetailUserModel> = detail
    }
}