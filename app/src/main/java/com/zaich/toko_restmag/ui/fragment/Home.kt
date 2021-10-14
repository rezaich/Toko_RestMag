package com.zaich.toko_restmag

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.google.gson.JsonObject
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.FragmentHomeBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.model.TransactionData
import com.zaich.toko_restmag.model.UserProfile
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.ui.adapter.MenuAdapter
import com.zaich.toko_restmag.ui.adapter.TransactionAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding
    lateinit var sharedPref: SharedPreferences
    var token: String = ""
    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    private lateinit var adapter : TransactionAdapter
    private var list = arrayListOf<TransactionData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        adapter = TransactionAdapter(list, requireActivity())
        adapter.notifyDataSetChanged()

        showLoading(true)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref =
                requireActivity().getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            serverInterface.getTransaction("Bearer " + token).enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>,
                ) {
                    Log.d("Home","Data penjualan")
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")
                    val arrayItem = ArrayList<TransactionData>()
                    for (i in 0 until myData.size()){
                        var myRecord = myData.get(i).asJsonObject
                        var id  = myRecord.get("id").asInt
                        var total = myRecord.get("total").asInt
                        var date = myRecord.get("deposit_date").asString
                        var time = myRecord.get("deposit_time").asString

                        Log.d("Log "+i.toString(), myData.get(i).toString())
                        arrayItem.add(TransactionData( id,total,date,time))
                    }

                    Log.d("Array Item", arrayItem.toString())
                    adapter.setUser(arrayItem)
                    binding?.apply {
                        rvTrans.layoutManager = LinearLayoutManager(activity)
                        rvTrans.adapter = adapter
                        rvTrans.setHasFixedSize(true)
                    }
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

