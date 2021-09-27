package com.zaich.toko_restmag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.zaich.toko_restmag.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    lateinit var sharePref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
//            LoginRecord()
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.crAccount.setOnClickListener {
            startActivity(Intent(application,RegisterActivity::class.java))
        }
    }
    fun LoginRecord(){
        val user_name = binding.etUserName.text.toString()
        val password = binding.etpassword.text.toString()

        if( user_name == "" || password == "" ){
            Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
        }
        else {
//            val loginUser : LoginResponse = LoginResponse(token = null)

            var apiInterface: Apinterface = ApiClient().getApiClient()!!.create(Apinterface::class.java)

            var requestCall : Call<LoginResponse> = apiInterface.login(user_name,password)

            requestCall.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("log", response.body()?.token.toString())
                        val token: String = response.body()?.token.toString()

                        //untuk menyimpan ke dalam sharePreferences
                        sharePref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharePref.edit()
                        editor.putString("token", token)
                        editor.apply()

                        Toast.makeText(
                            this@LoginActivity,
                            "Login Success", Toast.LENGTH_SHORT).show()
                        val home= Intent(application,MainActivity::class.java)
                        startActivity(home)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "User Name / Password Salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

}