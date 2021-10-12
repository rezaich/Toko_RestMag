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
import com.zaich.toko_restmag.databinding.FragmentAkunBinding
import com.zaich.toko_restmag.ui.activity.LoginActivity
import com.zaich.toko_restmag.ui.viewmodel.AkunViewModel
import androidx.fragment.app.viewModels
import com.google.gson.JsonObject
import com.zaich.toko_restmag.model.UserProfile
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.ui.activity.CreateProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Akun : Fragment() {

    private var _binding : FragmentAkunBinding? = null
    private val binding get() =_binding
    lateinit var sharedPref: SharedPreferences
    private val AkunViewModel : AkunViewModel by viewModels()
    var token: String = ""

    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)


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

        AkunViewModel.getLogout().observe(viewLifecycleOwner, {
            if (it != null) {
                Toast.makeText(activity, "Terlogout", Toast.LENGTH_SHORT).show()
            }
        })

        binding?.ibEditProfile?.setOnClickListener {
            startActivity(Intent(activity, CreateProfileActivity::class.java))
        }

        binding?.btnLogout?.setOnClickListener {
            AkunViewModel.setLogout()
            startActivity(Intent(activity, LoginActivity::class.java))
            showLoading(true)
//

            val SDK_INT = Build.VERSION.SDK_INT
            if (SDK_INT > 8) {
                val policy = StrictMode.ThreadPolicy.Builder()
                    .permitAll().build()
                StrictMode.setThreadPolicy(policy)
                //your codes here
                sharedPref =
                    requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                token = sharedPref.getString("token", "")!!

                serverInterface.showDetail("Bearer " + token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        Log.d("Akun","isi akun")
                        val myJson = response.body()
                        val myData = myJson!!.getAsJsonArray("user")
                        val arrayItem = ArrayList<UserProfile>()
                        for (i in 0 until myData.size()){
                            var myRecord = myData.get(i).asJsonObject
                            var name = myRecord.get("name").asString
                            var address = myRecord.get("address").asString
                            var phone = myRecord.get("phone").asString

                            Log.d("Log "+i.toString(), myData.get(i).toString())
//                            arrayItem.add(UserProfile(name, address,phone,))
                            binding?.tvName?.text = name
                            binding?.tvAddress?.text = address
                            binding?.tvPhone?.text = phone
                        }

                        binding
                        showLoading(false)

                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                        Log.d("Akun",t.message.toString())

                    }

                })

            }
        }
        binding?.btnLogout?.setOnClickListener {
            AkunViewModel.setLogout()
            startActivity(Intent(activity, LoginActivity::class.java))
            showLoading(true)
//

            val SDK_INT = Build.VERSION.SDK_INT
            if (SDK_INT > 8) {
                val policy = StrictMode.ThreadPolicy.Builder()
                    .permitAll().build()
                StrictMode.setThreadPolicy(policy)
                //your codes here
                sharedPref =
                    requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                token = sharedPref.getString("token", "")!!

                serverInterface.showDetail("Bearer " + token).enqueue(object : Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        Log.d("Akun","isi akun")
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("Akun",t.message.toString())

                    }

                })

            }
        }

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref =
                requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            serverInterface.showDetail("Bearer " + token).enqueue(object : Callback<JsonObject>{
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>,
                ) {
                    Log.d("Akun","isi akun")
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonObject("user")
                        var name = myData.get("name").asString
                        var address = myData.get("address").asString
                        var phone = myData.get("phone").asString

                        Log.d("Log ", myData.toString())
//                            arrayItem.add(UserProfile(name, address,phone,))
                        binding?.tvName?.text = name
                        binding?.tvAddress?.text = address
                        binding?.tvPhone?.text = phone

                    binding
                    showLoading(false)

                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    Log.d("Akun",t.message.toString())

                }

            })

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