package com.zaich.toko_restmag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.zaich.toko_restmag.databinding.ActivityCreateProfileBinding
import com.zaich.toko_restmag.model.DetailUserModel
import com.zaich.toko_restmag.ui.viewmodel.CreateProfileViewModel

class CreateProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateProfileBinding
    private val viewModel : CreateProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getdetail().observe(this,{
            if(it != null){
                Toast.makeText(this, "Detail terkirim", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnCreateProfile.setOnClickListener {
            sendData()
        }

        viewModel.getdetail().observe(this,{
            if (it != null){
                showLoading(false)
                Toast.makeText(this, "DATA", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendData(){
        val address  = binding.etAddress.text.toString()
        val phone = binding.etPhone.text.toString()
        val image = "jpeg"

        showLoading(true)

        if (address.isNotEmpty()||phone.isNotEmpty()){
            val detail = DetailUserModel(address,phone,image)
            viewModel.setDetail(detail)
        }else{
            Toast.makeText(this, "isi field terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbSearch.visibility = View.VISIBLE
            binding.pbSearch.visibility = View.GONE
        }
    }
}