package com.zaich.toko_restmag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaich.toko_restmag.databinding.FragmentAkunBinding
import com.zaich.toko_restmag.ui.activity.LoginActivity

class Akun : Fragment() {

    private var _binding : FragmentAkunBinding? = null
    private val binding get() =_binding
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

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

            val SDK_INT = Build.VERSION.SDK_INT
            if (SDK_INT > 8) {
                val policy = StrictMode.ThreadPolicy.Builder()
                    .permitAll().build()
                StrictMode.setThreadPolicy(policy)
                //your codes here
                sharedPref =
                    requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                token = sharedPref.getString("token", "")!!
            }
        }
    }
}