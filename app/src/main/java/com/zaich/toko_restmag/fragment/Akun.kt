package com.zaich.toko_restmag

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaich.toko_restmag.databinding.FragmentAkunBinding

class Akun : Fragment() {

    private var _binding : FragmentAkunBinding? = null
    private val binding get() =_binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAkunBinding.bind(view)

        binding?.btnLogout?.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}