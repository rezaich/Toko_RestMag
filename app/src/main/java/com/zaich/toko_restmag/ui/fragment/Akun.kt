package com.zaich.toko_restmag

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.JsonObject
import com.zaich.toko_restmag.databinding.FragmentAkunBinding
import com.zaich.toko_restmag.ui.activity.LoginActivity
import retrofit2.Call
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.server.ApiClient
import retrofit2.Callback
import retrofit2.Response
import com.zaich.toko_restmag.model.LogoutResponse
import com.zaich.toko_restmag.ui.viewmodel.LoginViewModel
import org.json.JSONObject
import com.zaich.toko_restmag.ui.viewmodel.AkunViewModel
import androidx.activity.viewModels
import androidx.fragment.app.viewModels


class Akun : Fragment() {

    private var _binding : FragmentAkunBinding? = null
    private val binding get() =_binding
    lateinit var sharedPref: SharedPreferences
    private val AkunViewModel : AkunViewModel by viewModels()
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

        AkunViewModel.getLogout().observe(viewLifecycleOwner,{
            if (it != null){
                Toast.makeText(activity, "Terlogout", Toast.LENGTH_SHORT).show()
            }
        })




        binding?.btnLogout?.setOnClickListener {
            AkunViewModel.setLogout()
            startActivity(Intent(activity, LoginActivity::class.java))
//

//            val SDK_INT = Build.VERSION.SDK_INT
//            if (SDK_INT > 8) {
//                val policy = StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build()
//                StrictMode.setThreadPolicy(policy)
//                //your codes here
//                sharedPref =
//                    requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
//                token = sharedPref.getString("token", "")!!
//
//                var apiInterface: ApiInterface = ApiClient().getApiClient()!!
//                    .create(ApiInterface::class.java)
//                var requestCall: Call<JsonObject> =
//                    apiInterface.logout("Bearer "+token)
//                requestCall.enqueue(object :Callback<JsonObject>{
//                    override fun onResponse(
//                        call: Call<JsonObject>,
//                        response: Response<JsonObject>
//                    ) {
//                       Log.d("logout pasti","logout")
//
//                        Toast.makeText(activity, "logout pasti", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(activity, LoginActivity::class.java))
//                    }
//
//                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                        Log.d("logout",t.message.toString())
//                    }
//
//                })
//            }
        }
    }
}