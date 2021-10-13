package com.zaich.toko_restmag.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.databinding.ActivityRegisterBinding
import com.zaich.toko_restmag.ui.viewmodel.RegisterMainViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registViewModel: RegisterMainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegis.setOnClickListener {
            register()
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        registViewModel.getRegist().observe(this, {
            if (it != null) {
                Toast.makeText(this, "Register Successfull", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }

    private fun register() {
        val name = binding.eName.text.toString()
        val userName = binding.euserName.text.toString()
        val password = binding.epassword.text.toString()
        val ePassword = binding.epassword.text.toString()
        val isAdmin = "1"

        if (name.isEmpty() || userName.isEmpty() || password.isEmpty() || ePassword.isEmpty() || isAdmin.isEmpty()) {
            Toast.makeText(this, "ada field yang kosong", Toast.LENGTH_SHORT).show()

        } else if (password != ePassword) {
            Toast.makeText(this, "Repeat Password tidak sama", Toast.LENGTH_SHORT).show()
        } else {
            val newUser = UserModel(name, userName, password, isAdmin)
            registViewModel.setRegist(newUser)
            showLoading(true)
            val message = Log.d("regist", "register Successfull").toString()
            if (message != null) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "tidak masuk", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbSearch.visibility = View.VISIBLE
        } else {
            binding.pbSearch.visibility = View.GONE
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}