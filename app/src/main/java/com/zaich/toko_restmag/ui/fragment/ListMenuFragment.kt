package com.zaich.toko_restmag.ui.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zaich.toko_restmag.ui.adapter.MenuAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.zaich.toko_restmag.databinding.FragmentListMenuBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListMenuFragment : Fragment() {

    private var _binding : FragmentListMenuBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: MenuAdapter
    private var list = arrayListOf<MenuModel>()

    private val serverInterface: ApiInterface =
        ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentListMenuBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListMenuBinding.bind(view)

        adapter = MenuAdapter(list, requireActivity())
        adapter.notifyDataSetChanged()

        showLoading(true)


        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref = requireActivity().getSharedPreferences(
                "SharePref",
                Context.MODE_PRIVATE
            )
            token = sharedPref.getString("token", "")!!
            serverInterface.showProduct("Bearer " + token).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("menu", "List Menu")

                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")
                    val arrayItem = ArrayList<MenuModel>()
                    for (i in 0 until myData.size()){
                        var myRecord = myData.get(i).asJsonObject
                        var price = myRecord.get("price").asInt
                        var name = myRecord.get("name").asString
                        var desc = myRecord.get("description").asString
                        var categoryid = myRecord.get("category_id").asInt

                        Log.d("Log "+i.toString(), myData.get(i).toString())
                        arrayItem.add(MenuModel( name,price,desc,categoryid))
                    }

                    Log.d("Array Item", arrayItem.toString())
                    adapter.setMenu(arrayItem)

                    binding?.apply {
                        rvListMenu.layoutManager = LinearLayoutManager(activity)
                        rvListMenu.adapter = adapter
                        rvListMenu.setHasFixedSize(true)
                    }

                    showLoading(false)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
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