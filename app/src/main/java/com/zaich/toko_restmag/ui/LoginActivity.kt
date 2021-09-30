package com.zaich.toko_restmag.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zaich.toko_restmag.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding : ActivityLoginBinding
    lateinit var sharePref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

    }
}