package com.zaich.toko_restmag.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.LayoutCreateMenuBinding
import com.zaich.toko_restmag.ui.viewmodel.CreateMenuViewModel
import com.zaich.toko_restmag.model.MenuModel

class AddMakananFragment :Fragment(){
    private var _binding  : LayoutCreateMenuBinding?  = null
    private val binding get() = _binding
    private val viewModelMenu : CreateMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = LayoutCreateMenuBinding.bind(view)

        binding?.ibMenu?.setOnClickListener {
            val intent = Intent().apply{
                setType("image/*")
                setAction(Intent.ACTION_GET_CONTENT)
            }
            startActivity(intent)
        }

        viewModelMenu.getMenu().observe(viewLifecycleOwner,{
            if (it != null){
                Toast.makeText(activity, "data masuk", Toast.LENGTH_SHORT).show()
            }
        })

        binding?.btSaveMenu?.setOnClickListener {
            val name  = binding?.etMenuName?.text.toString()
            val price = binding?.etMenuPrice?.text.toString().toInt()
            val desc = binding?.etMenuDesc?.text.toString()
//            val image = "jpeg"
            val category = 1

            if (name.isNotEmpty() || desc.isNotEmpty() ){
                val menu = MenuModel(name,price,desc,category)
                viewModelMenu.setMenu(menu)
            }
        }

    }
}