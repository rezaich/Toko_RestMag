package com.zaich.toko_restmag.pegawai.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zaich.toko_restmag.databinding.ActivityLoginPegawaiBinding
import com.zaich.toko_restmag.model.LoginResponse
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPegawaiActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginPegawaiBinding
    private lateinit var sharePref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPegawaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener (this)

    }

    private fun showLoading(state: Boolean){
        if (state) {
            binding.pbSearch.visibility = View.VISIBLE
        } else {
            binding.pbSearch.visibility = View.GONE
        }
    }
    override fun onClick(v: View?) {
        val user_name = binding.etLogUserName.text.toString()
        val password = binding.etLogPass.text.toString()

        if( user_name == "" || password == "" ){
            Toast.makeText(this, "Masih ada field yang kosong", Toast.LENGTH_LONG).show()
        } else {
            showLoading(true)

            val apiInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
            val requestCall : Call<LoginResponse> = apiInterface.login(user_name,password)
            requestCall.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("login Failure", t.message.toString())
                    Toast.makeText(this@LoginPegawaiActivity, "Failed", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("login log", response.body()?.token.toString())
                        val token: String = response.body()?.token.toString()
                        showLoading(false)

                        //untuk menyimpan ke dalam sharePreferences
                        sharePref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharePref.edit()
                        editor.putString("token", token)
                        editor.apply()

                        Toast.makeText(this@LoginPegawaiActivity, "Login Success", Toast.LENGTH_SHORT).show()
                        val home= Intent(application, CategoryActivity::class.java)
                        startActivity(home)
                        finish()
                    } else {
                        Log.d("login token", response.body()?.token.toString())
                        Toast.makeText(this@LoginPegawaiActivity, "User Name / Password Salah", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            })
        }
    }
//    override fun onStart() {
//        super.onStart()
//        sharePref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
//        val currentUser = sharePref
//        val editor = currentUser.edit()
//        editor.apply()
//
//        if (editor != null){
//            Intent(this, MainActivity::class.java).also {
//                startActivities(arrayOf(it))
//                editor.clear()
//                editor.apply()
//                finish()
//            }
//        }
//    }
}