package com.zaich.toko_restmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val intent = Intent(application,LoginActivity::class.java)
        btRegis.setOnClickListener {
            startActivity(intent)
        }
    }
}