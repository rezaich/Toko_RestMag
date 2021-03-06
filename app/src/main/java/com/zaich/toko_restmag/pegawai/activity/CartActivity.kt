package com.zaich.toko_restmag.pegawai.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.ActivityCartBinding
import com.zaich.toko_restmag.pegawai.adapter.CartAdapter
import com.zaich.toko_restmag.pegawai.model.CartModel
import com.zaich.toko_restmag.pegawai.model.DefaultResponse
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding

    var myAdapter: CartAdapter? = null
    var token: String = ""
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "PEMESANAN"

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            val apiInterface: ApiInterface =
                ApiClient().getApiClient()!!.create(ApiInterface::class.java)
            val requestCall: Call<JsonObject> = apiInterface.getCartsByUser("Bearer $token")
            val progressDialog: ProgressDialog = ProgressDialog(this@CartActivity)
            progressDialog.setMessage("Loading")
            progressDialog.show();
            requestCall.enqueue(object : Callback<JsonObject> {

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    progressDialog.dismiss()
                    Log.d("Log cart gagal", t.toString())
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("My Log cart", response.body().toString())
                    progressDialog.dismiss()
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")

                    Log.d("My Log cart", myData.size().toString())
                    myAdapter = CartAdapter(this@CartActivity)

                    val cartList = ArrayList<CartModel>()
                    for (i in 0 until myData.size()) {
                        val myRecord: JsonObject = myData.get(i).asJsonObject
//                        var id = myRecord.get("id").asInt

                        val product = myRecord!!.getAsJsonObject("product")
                        val nmProduct = product!!.get("name").asString
                        val price = myRecord.get("price").asInt
                        val quantity = myRecord.get("quantity").asInt
                        val daydate = myRecord.get("daydate").asString
                        val daytime = myRecord.get("daytime").asString

                        Log.d("Log " + i.toString(), myData.get(i).toString())
                        cartList.add(CartModel(nmProduct, price, quantity, daydate, daytime))
                    }
                    Log.d("Array Item", cartList.toString())
                    myAdapter!!.setData(cartList)

                    var totalItem = 0
                    var totalHargaPerItem: Int = 0
                    var grandTotal = 0
                    cartList.forEach {
                        totalHargaPerItem = (it.totalProduct) * (it.priceOfProduct)
                        totalItem += it.totalProduct
                        grandTotal += totalHargaPerItem
                    }
                    binding.tvTotalProduct.text = totalItem.toString()
                    binding.tvTotalHarga.text = grandTotal.toString()
                    binding.rvCart.layoutManager = LinearLayoutManager(this@CartActivity)
                    binding.rvCart.adapter = myAdapter

                    val calendar = Calendar.getInstance()
                    val currentDate: Date = Calendar.getInstance().time
                    val currentDaydate =
                        DateFormat.getDateInstance(DateFormat.FULL).format(currentDate)
                    val currentTime = SimpleDateFormat("HH:mm:ss").format(calendar.time)
                    binding.daydate.text = currentDaydate
                    binding.daytime.text = currentTime

//                    Intent(this@CartActivity, SendActivity::class.java).also {
//                        val total = binding.tvTotalHarga.text
//                        val date = binding.daydate.text
//                        val time = binding.daytime.text
//                        it.putExtra("CART_TOTAL", total)
//                        it.putExtra("CART_DATE", date)
//                        it.putExtra("CART_TIME", time)
//                        startActivity(it)
//                    }

                }
            })
        }
//        binding.btnCheckout.setOnClickListener {
//            Intent(applicationContext, DataPemesanActivity::class.java).also {
//                startActivities(arrayOf(it))
//            }
    }

    private fun addTransactions() {
        sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)

        val token = sharedPref.getString("token", "")!!
        val total = binding.tvTotalHarga.text.toString().toInt()
        val daydate = binding.daydate.text.toString()
        val daytime = binding.daytime.text.toString()

        Log.d("Log cart", total.toString())

        val apiInterface: ApiInterface = ApiClient()
            .getApiClient()!!
            .create(ApiInterface::class.java)
        val requestCall: Call<DefaultResponse> = apiInterface
            .addTransaction("Bearer $token", total, daydate, daytime)
        requestCall.enqueue(object : Callback<DefaultResponse> {
            override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                Toast.makeText(
                    baseContext, "Data gagal ditambahkan",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("log transactions fail", t.toString())
            }

            override fun onResponse(
                call: Call<DefaultResponse>,
                response: Response<DefaultResponse>
            ) {
                Log.d("log transactions", response.toString())
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CartActivity,
                        "Data berhasil ditambahkan ke transaction",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("log transactions", response.body()?.success.toString())
                    Log.d("Log transactions", response.body()?.message.toString())
                    val intent = Intent(
                        this@CartActivity,
                        CartActivity::class.java
                    )
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@CartActivity,
                        "Data gagal ditambahkan ke transaction ",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("log transactions", response.body().toString() + token)
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send -> {
                addTransactions()
//                val intent = Intent(this, SendActivity::class.java)
//                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
