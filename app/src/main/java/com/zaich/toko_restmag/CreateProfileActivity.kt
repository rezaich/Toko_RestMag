package com.zaich.toko_restmag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaich.toko_restmag.databinding.ActivityCreateProfileBinding

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}