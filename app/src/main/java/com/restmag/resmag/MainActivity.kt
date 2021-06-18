package com.restmag.resmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var tvManagement : TextView
    lateinit var tvRestaurant : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvManagement = findViewById(R.id.tv_management_main)
        tvManagement.setOnClickListener {
            Toast.makeText(this, "Management Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        tvRestaurant = findViewById(R.id.tv_restaurant_main)
        tvRestaurant.setOnClickListener {
            Toast.makeText(this, "Restaurant Clicked", Toast.LENGTH_SHORT).show()
        }
    }
}