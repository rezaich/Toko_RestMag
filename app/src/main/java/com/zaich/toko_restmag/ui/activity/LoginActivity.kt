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
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.zaich.toko_restmag.ui.viewmodel.LoginViewModel
import com.zaich.toko_restmag.databinding.ActivityLoginBinding
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Response
import com.zaich.toko_restmag.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import kotlin.math.log

class LoginActivity : AppCompatActivity(),View.OnClickListener{
    private lateinit var binding : ActivityLoginBinding
    private lateinit var sharePref : SharedPreferences
    private val loginViewModel : LoginViewModel by viewModels()
    private var response : Response<LoginResponse>? = null
    private var model : LoginResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        binding.btnLogin.setOnClickListener{
            login()
//            val intent = Intent(this,MainActivity::class.java)
////            startActivity( Intent(this,MainActivity::class.java))
//            if (model?.token != null){
//                startActivity(intent)
//            }else{
//                Toast.makeText(this, "tidak masuk", Toast.LENGTH_SHORT).show()
//            }
        }*/

        binding.btnLogin.setOnClickListener(this)

        binding.crAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

/*        loginViewModel.getLogin().observe(this,{
            if (it != null){
                Toast.makeText(this, "mantap", Toast.LENGTH_SHORT).show()
                showLoading(false)


//                if (it.token != null){
//                    startActivity(intent)
//                }
            }
        })*/
    }

/*    private fun login (){
        val userName = binding.etLogUserName.text.toString()
        val password = binding.etLogPass.text.toString()
        loginViewModel.setLogin(userName,password)
        showLoading(true)

        if (userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Masih ada Field yang kosong", Toast.LENGTH_SHORT).show()
        }else{

            Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()
        }
    }*/

    private fun showLoading(state: Boolean) {
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
        }
        else {
//            val loginUser : LoginResponse = LoginResponse(token = null)
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
}