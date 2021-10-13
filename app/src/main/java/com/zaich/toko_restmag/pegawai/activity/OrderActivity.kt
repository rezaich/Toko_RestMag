package com.zaich.toko_restmag.pegawai.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.ActivityOrderBinding
import com.zaich.toko_restmag.pegawai.model.DefaultResponse
import com.zaich.toko_restmag.pegawai.model.ProductIntent
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import com.zaich.toko_restmag.ui.activity.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    var totHarga    :Int    = 0
    var minInteger  :Int    = 0
    var MIN_NUMBER          = 0
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        val productIntent: ProductIntent = intent.getSerializableExtra("EXTRA_PRODUCT") as ProductIntent
        val aId = productIntent.id
        val aProduct = productIntent.productName
        val aPrice = productIntent.price
        actionBar.title = "Order $aProduct"
        //id pada activity_order.xml
        binding.apply {
            OrdId.text          = aId.toString()
            OrdProduct.text     = aProduct
            OrdPrice.text       = aPrice.toString()
        }
        fun display(number: Int) {
            val displayInteger = findViewById<View>(R.id.JmlOrd) as TextView

            displayInteger.text = "" + number

            binding.apply {
                totHarga = OrdPrice.text.toString().toInt() *
                        displayInteger.text.toString().toInt()
                TotHarOrd.text = totHarga.toString()
            }
        }
        binding.decreaseOrd.setOnClickListener {
            if(minInteger == MIN_NUMBER){
                minInteger = 0
            }
            else{
                minInteger -= 1
                display(minInteger)
            }
        }
        binding.increaseOrd.setOnClickListener{
            minInteger += 1
            display(minInteger)
        }
        binding.btnTambahKeranjang.setOnClickListener {
            addProductToCart()
        }

        val calendar = Calendar.getInstance()
        val currentDate: Date = Calendar.getInstance().time
        val currentDaydate = DateFormat.getDateInstance(DateFormat.FULL).format(currentDate)
        val currentTime = SimpleDateFormat("HH:mm:ss").format(calendar.time)

        binding.daydate.text = currentDaydate
        binding.daytime.text = currentTime
    }

    private fun addProductToCart() {
        sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)

        val token = sharedPref.getString("token","")!!
        val idProduct = binding.OrdId.text.toString().toInt()
//        var name = "name"
//        var nmProduct = OrdProduct.text.toString()
        val price = binding.OrdPrice.text.toString().toInt()
        val qty = binding.JmlOrd.text.toString().toInt()
        val daydate = binding.daydate.text.toString()
        val daytime = binding.daytime.text.toString()

//        Intent(this@OrderActivity, StrukActivity::class.java).also {
//            it.putExtra("ORDER_ID", idProduct)
//            it.putExtra("ORDER_PRICE", price)
//            it.putExtra("ORDER_QTY", qty)
//            it.putExtra("ORDER_DAYDATE", daydate)
//            it.putExtra("ORDER_DAYTIME", daytime)
//        }

        if(qty == 0){
            return Toast.makeText(this, "Jumlah beli masih kosong", Toast.LENGTH_SHORT).show()
        }else{

            val apiInterface: ApiInterface = ApiClient()
                .getApiClient()!!
                .create(ApiInterface::class.java)
            val requestCall: Call<DefaultResponse> = apiInterface
                .addProductToCart("Bearer $token", idProduct, price, qty, daydate, daytime)
            requestCall.enqueue(object : Callback<DefaultResponse> {
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(baseContext, "Data gagal ditambahkan ke keranjang ",
                        Toast.LENGTH_SHORT).show()
                    Log.d("log order", t.toString())
                }

                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Log.d("logggg", response.toString())
                    if(response.isSuccessful){
                        Toast.makeText(this@OrderActivity,
                            "Data berhasil ditambahkan ke keranjang",
                            Toast.LENGTH_SHORT).show()
                        Log.d("log", response.body()?.success.toString())
                        Log.d("Log ordersss", response.body()?.message.toString())
                        val intent = Intent(this@OrderActivity,
                            CategoryActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this@OrderActivity,
                            "Data gagal ditambahkan ke keranjang ya ",
                            Toast.LENGTH_SHORT).show()
                        Log.d("log order", response.body().toString()+token)
                    }
                }
            })
            // di pindahin ke transaction
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}