package com.zaich.toko_restmag.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.model.PegawaiModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateUserViewModel(application: Application) : AndroidViewModel(application) {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val createUser = MutableLiveData<PegawaiModel>()
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    fun setCreate(pegawaiModel: PegawaiModel){
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
            serverInterface.createUser("Bearer " + token, pegawaiModel)
                .enqueue(object : Callback<PegawaiModel> {
                    override fun onResponse(
                        call: Call<PegawaiModel>,
                        response: Response<PegawaiModel>
                    ) {
                        if (response.isSuccessful) createUser.postValue(response.body())
                        Log.d("Regist", "Register successfully")
                    }

                    override fun onFailure(call: Call<PegawaiModel>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                    }
                })
        }
    }

    fun getCreate(): MutableLiveData<PegawaiModel> = createUser
}