package com.zaich.toko_restmag.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.databinding.ActivityRegisterBinding
import com.zaich.toko_restmag.ui.viewmodel.RegisterMainViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registViewModel : RegisterMainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(application, LoginActivity::class.java)
        binding.btRegis.setOnClickListener {
            startActivity(intent)
        }

        registViewModel.getRegist().observe(this,{
            if (it != null){
                Toast.makeText(this, "tidak kosong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun register(){
        val name = binding.eName.text.toString()
        val userName  = binding.euserName.text.toString()
        val password = binding.epassword.text.toString()
        val ePassword = binding.epassword.text.toString()

        if (name.isEmpty() || userName.isEmpty()|| password.isEmpty()||ePassword.isEmpty()){
            Toast.makeText(this, "ada field yang kosong", Toast.LENGTH_SHORT).show()
            if (password == ePassword){
                Toast.makeText(this, "Repeat Password tidak sama", Toast.LENGTH_SHORT).show()
            }else{
                val newUser = UserModel(name,userName,password)
                registViewModel.setRegist(newUser)
            }
        }
    }
}