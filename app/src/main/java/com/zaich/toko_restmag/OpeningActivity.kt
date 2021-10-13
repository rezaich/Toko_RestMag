package com.zaich.toko_restmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.zaich.toko_restmag.databinding.ActivityOpeningBinding
import com.zaich.toko_restmag.pegawai.activity.LoginPegawaiActivity
import com.zaich.toko_restmag.ui.activity.LoginActivity

class OpeningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpeningBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpeningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar : ActionBar? = supportActionBar
//        actionBar!!.setDisplayShowHomeEnabled(true)
//        actionBar!!.title = ""

        binding.loginAsAdmin.setOnClickListener {
            Intent(this, LoginActivity::class.java).also {
                startActivities(arrayOf(it))
                finish()
            }
        }
        binding.loginAsEmployee.setOnClickListener {
            Intent(this, LoginPegawaiActivity::class.java).also {
                startActivities(arrayOf(it))
                finish()
            }
        }
    }
}