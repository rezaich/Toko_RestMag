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
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.zaich.toko_restmag.databinding.ActivityCartBinding
import com.zaich.toko_restmag.pegawai.adapter.CartAdapter
import com.zaich.toko_restmag.pegawai.model.CartModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//                        val cartlModel = StrukModel(nmProduct, price, quantity, daydate, daytime)
//                        Intent(this@CartActivity, StrukActivity::class.java).also {
//                            it.putExtra("EXTRA_STRUK", cartlModel)
//                        }
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

                }


            })
        }
//        binding.btnCheckout.setOnClickListener {
//            Intent(applicationContext, DataPemesanActivity::class.java).also {
//                startActivities(arrayOf(it))
//            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
