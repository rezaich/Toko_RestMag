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
import com.bumptech.glide.Glide
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.LayoutCreateMenuBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.ui.viewmodel.CreateMenuViewModel
import java.lang.Exception

class AddMinumanFragment : Fragment() {
    private var _binding: LayoutCreateMenuBinding? = null
    private val binding get() = _binding
    private val viewModelMenu: CreateMenuViewModel by viewModels()
    private var imageUri: Uri? = null


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

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                try {
                    if (it?.resultCode == Activity.RESULT_OK) {
                        it.data?.let {
                            imageUri = it.data
                            Glide.with(this).load(imageUri)
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

        binding?.ibMenu?.setOnClickListener {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            resultLauncher.launch(intent)
        }

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

            if (name.isNotEmpty() || desc.isNotEmpty()) {
                imageUri?.let {
                    val contentResolver: ContentResolver = requireActivity().contentResolver
                    val mimeTypeMap: MimeTypeMap = MimeTypeMap.getSingleton()
                    val fileExtension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(it))
                    var image = fileExtension
                    val menu = MenuModel(name, price, desc, image!!, category)
                    viewModelMenu.setMenu(menu)
                }
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