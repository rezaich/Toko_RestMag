package com.restmag.resmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent= Intent(application, HomeActivity::class.java)
        btnLogin.setOnClickListener {
            startActivity(intent)
        }

        crAccount.setOnClickListener {
            startActivity(Intent(application, RegisterActivity::class.java))
        }
    }
}