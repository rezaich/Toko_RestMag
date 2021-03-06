package com.zaich.toko_restmag.ui.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.LayoutCreateMenuBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.ui.viewmodel.CreateMenuViewModel
import java.lang.Exception

class AddMinumanFragment : Fragment() {
    private var _binding: LayoutCreateMenuBinding? = null
    private val binding get() = _binding
    private val viewModelMenu: CreateMenuViewModel by viewModels()


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



        viewModelMenu.getMenu().observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(activity, "data masuk", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })

        binding?.btSaveMenu?.setOnClickListener {
            val name = binding?.etMenuName?.text.toString()
            val price = binding?.etMenuPrice?.text.toString().toInt()
            val desc = binding?.etMenuDesc?.text.toString()
            val category = 2

            showLoading(true)

            if (name.isNotEmpty() || desc.isNotEmpty()) {
                    val menu = MenuModel(name, price, desc, category)
                    viewModelMenu.setMenu(menu)
                    Toast.makeText(activity, "data masuk", Toast.LENGTH_SHORT).show()
                    showLoading(false)
            } else {
                Toast.makeText(activity, "isi field terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.pbSearch?.visibility = View.VISIBLE
        } else {
            binding?.pbSearch?.visibility = View.GONE
        }
    }
}