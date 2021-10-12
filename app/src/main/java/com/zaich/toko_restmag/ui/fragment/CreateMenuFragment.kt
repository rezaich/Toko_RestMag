package com.zaich.toko_restmag.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.FragmentCreateMenuBinding

class CreateMenuFragment : Fragment() {

    private var _binding  : FragmentCreateMenuBinding?  = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCreateMenuBinding.bind(view)

        binding?.btMakanan?.setOnClickListener {
//            startActivity(Intent(requireActivity(),AddMakananFragment::class.java))

            val mCategoryFragment = AddMakananFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mCategoryFragment, AddMakananFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
        binding?.btMinuman?.setOnClickListener {
            val mCategoryFragment = AddMinumanFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.beginTransaction().apply {
                replace(R.id.frameContainer, mCategoryFragment, AddMinumanFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}