package com.zaich.toko_restmag.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Intent as Intent

class LoginViewModel(application: Application) : AndroidViewModel(application)  {
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private val userLogin = MutableLiveData<LoginResponse>()
//    private lateinit var sharePref : SharedPreferences


    fun setLogin(user_name : String , password : String){
        serverInterface.login(user_name, password).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful){
                    Log.d(TAG, response.body()?.token.toString())
//                    var token = response.body()?.token.toString()
//                    Log.d("login",token)
//                    sharePref = getApplication<Application>().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
//                    val editor: SharedPreferences.Editor = sharePref.edit()
//                    editor.putString("token", token)
//                    editor.apply()
                    Toast.makeText(getApplication(), "Login Success", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
//                    val application = getApplication<Application>()
                    val home= Intent(getApplication(),MainActivity::class.java)

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