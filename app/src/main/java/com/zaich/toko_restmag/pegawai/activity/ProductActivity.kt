package com.zaich.toko_restmag.pegawai.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.gson.JsonObject
import com.zaich.toko_restmag.databinding.ActivityProductBinding
import com.zaich.toko_restmag.pegawai.adapter.ProductAdapter
import com.zaich.toko_restmag.pegawai.model.ProductModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBar: ActionBarDrawerToggle
    private lateinit var navDrawerView: NavigationView
    private lateinit var bottomNavigation : BottomNavigationView
    lateinit var sharedPref: SharedPreferences

    var token: String = ""
    var myAdapter : ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            //get category id from intent
            val categoryId  = intent.getIntExtra("categoryId", 0)
            val categoryName  = intent.getStringExtra("categoryName")

            actionBar.title = "Menu $categoryName"
            Toast.makeText(this, "category $categoryName", Toast.LENGTH_SHORT).show()
            val apiInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
            val requestCall: Call<JsonObject> = apiInterface.getProductsByCategory("Bearer $token", categoryId)
            requestCall.enqueue(object: Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    Log.d("gagal", t.toString())
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("product log", response.body().toString())
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")

                    Log.d("product log", myData.size().toString())
                    myAdapter = ProductAdapter(this@ProductActivity)

                    val arrayItem = ArrayList<ProductModel>()
                    for (i in 0 until myData.size()) {
                        val myRecord: JsonObject = myData.get(i).asJsonObject
                        val id = myRecord.get("id").asInt
                        val name = myRecord.get("name").asString.toUpperCase()
                        val price = myRecord.get("price").asInt
                        val description = myRecord.get("description").asString

                        Log.d("product log $i", myData.get(i).toString())
                        arrayItem.add(ProductModel(id, name, price, description))
                    }
                    Log.d("Array Item", arrayItem.toString())
//                val myData2 : ArrayList<CategoryModel> = myData as ArrayList<CategoryModel>
                    myAdapter!!.setData(arrayItem)

                    binding.productRecycleview.layoutManager = LinearLayoutManager(this@ProductActivity)
                    binding.productRecycleview.adapter = myAdapter
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}