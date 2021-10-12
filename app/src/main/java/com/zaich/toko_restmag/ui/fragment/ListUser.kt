package com.zaich.toko_restmag

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
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.FragmentCreateUserBinding
import com.zaich.toko_restmag.databinding.FragmentListUserBinding
import com.zaich.toko_restmag.model.MenuModel
import com.zaich.toko_restmag.model.UserModel
import com.zaich.toko_restmag.model.Users
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.ui.adapter.UserAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListUser : Fragment() {

    private var _binding : FragmentListUserBinding? =null
    private val binding get() = _binding
    private lateinit var adapter:UserAdapter
    private var list = arrayListOf<Users>()

    private val serverInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
    lateinit var sharedPref: SharedPreferences
    var token: String = ""

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

        adapter = UserAdapter(list,requireContext())
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
            serverInterface.showUsers("Bearer " + token).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    Log.d("user", "List User")
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("users")
                    val arrayItem = ArrayList<Users>()
                    for (i in 0 until myData.size()){
                        var myRecord = myData.get(i).asJsonObject
                        var id = myRecord.get("id").asInt
                        var userName = myRecord.get("user_name").asString

                        Log.d("Log "+i.toString(), myData.get(i).toString())
                        arrayItem.add(Users( id,userName))
                    }

                    Log.d("Array Item", arrayItem.toString())
                    adapter.setUser(arrayItem)

                    binding?.apply {
                        rvUser.layoutManager = LinearLayoutManager(activity)
                        rvUser.adapter = adapter
                        rvUser.setHasFixedSize(true)
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