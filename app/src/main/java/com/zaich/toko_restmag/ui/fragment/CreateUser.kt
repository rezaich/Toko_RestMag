package com.zaich.toko_restmag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zaich.toko_restmag.databinding.FragmentCreateUserBinding
import com.zaich.toko_restmag.ui.viewmodel.CreateUserViewModel
import com.zaich.toko_restmag.model.PegawaiModel
import androidx.fragment.app.viewModels
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.ui.viewmodel.AkunViewModel


class CreateUser : Fragment() {

    private var _binding : FragmentCreateUserBinding? = null
    private val binding get() = _binding
    private val CreateViewModel : CreateUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCreateUserBinding.bind(view)

        binding?.btSave?.setOnClickListener {
            val name = binding?.etUsername?.text.toString()
            val phone = binding?.etPhone?.text.toString()

            if (name.isEmpty() || phone.isEmpty()){
                Toast.makeText(activity, "ada field yang kosong", Toast.LENGTH_SHORT).show()
            }else{
                val newUser = PegawaiModel(name, phone)
                CreateViewModel.setCreate(newUser)
            }
        }
    }
}