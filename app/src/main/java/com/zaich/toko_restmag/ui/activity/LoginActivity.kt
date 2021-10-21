package com.zaich.toko_restmag.ui.activity

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zaich.toko_restmag.OpeningActivity
import com.zaich.toko_restmag.databinding.ActivityLoginBinding
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Response
import com.zaich.toko_restmag.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity(),View.OnClickListener{
    private lateinit var binding : ActivityLoginBinding
    private lateinit var sharePref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnLogin.setOnClickListener(this)

        //btn register user
/*        binding.crAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }*/
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbSearch.visibility = View.VISIBLE
        } else {
            binding.pbSearch.visibility = View.GONE
        }
    }
    override fun onClick(v: View?) {
//        startActivity(Intent(this,MainActivity::class.java))
        val user_name = binding.etLogUserName.text.toString()
        val password = binding.etLogPass.text.toString()

        if( user_name == "" || password == "" ){
            Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
        }
        else {
    showLoading(true)

            var apiInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)

            var requestCall : Call<LoginResponse> = apiInterface.login(user_name,password)

            requestCall.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    Toast.makeText(this@LoginActivity, "Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("log", response.body()?.token.toString())
                        val token: String = response.body()?.token.toString()
                        showLoading(false)

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
                        showLoading(false)
                    }
                }
            })
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, OpeningActivity::class.java))
        return true
    }
}