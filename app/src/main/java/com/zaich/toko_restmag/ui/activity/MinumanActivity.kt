package com.zaich.toko_restmag.ui.activity

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.zaich.toko_restmag.databinding.LayoutCreateMenuBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.ui.viewmodel.CreateMenuViewModel

class MinumanActivity:AppCompatActivity(),View.OnClickListener {
    private lateinit var binding : LayoutCreateMenuBinding
    private val viewModelMenu : CreateMenuViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutCreateMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModelMenu.getMenu().observe(this,{
            if (it != null){
                Toast.makeText(this, "data masuk", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btSaveMenu.setOnClickListener (this)
    }

    override fun onClick(v: View?) {
        val name  = binding?.etMenuName?.text.toString()
        val price = binding?.etMenuPrice?.text.toString().toInt()
        val desc = binding?.etMenuDesc?.text.toString()
        val category = 2
        showLoading(true)

        if (name.isNotEmpty() || desc.isNotEmpty() ){
            val menu = MenuModel(name,price,desc,category)
            viewModelMenu.setMenu(menu)
            Toast.makeText(this, "data masuk", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this, "isi field terlebih dahulu", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbSearch.visibility = View.VISIBLE
        } else {
            binding.pbSearch.visibility = View.GONE
        }
    }

}