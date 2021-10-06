package com.zaich.toko_restmag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.FragmentCreateUserBinding
import com.zaich.toko_restmag.databinding.FragmentListUserBinding

class ListUser : Fragment() {

    private var _binding : FragmentListUserBinding? =null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListUserBinding.bind(view)
    }
}