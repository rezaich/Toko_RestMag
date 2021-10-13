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
import com.zaich.toko_restmag.databinding.ActivityCategoryBinding
import com.zaich.toko_restmag.pegawai.adapter.CategoryAdapter
import com.zaich.toko_restmag.pegawai.model.CategoryModel
import com.zaich.toko_restmag.server.ApiClient
import com.zaich.toko_restmag.server.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    //    private lateinit var bottomNavigation : BottomNavigationView
    lateinit var sharedPref: SharedPreferences
    var token: String? = null

    var myAdapter : CategoryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar : ActionBar? = supportActionBar
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar!!.title = "Menu Katagori"

        val progressDialog: ProgressDialog = ProgressDialog(this@CategoryActivity)
        progressDialog.setMessage("Loading")
        progressDialog.show();

        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
            sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
            token = sharedPref.getString("token", "")!!

            val apiInterface: ApiInterface = ApiClient().getApiClient()!!.create(ApiInterface::class.java)
            val requestCall: Call<JsonObject> = apiInterface.getCategories("Bearer $token")
            requestCall.enqueue(object: Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("mainLog gagal", t.toString())
                }

                override fun onResponse(call: Call<JsonObject>,
                                        response: Response<JsonObject>
                ) {
                    val myJson = response.body()
                    val myData = myJson!!.getAsJsonArray("data")
                    myAdapter = CategoryAdapter(this@CategoryActivity)

                    val arrayItem = ArrayList<CategoryModel>()
                    for (i in 0 until myData.size()) {
                        val myRecord: JsonObject = myData.get(i).asJsonObject
                        val id = myRecord.get("id").asInt
                        val name = myRecord.get("name").asString

                        Log.d("mainLog $i", myData.get(i).toString())
                        arrayItem.add(CategoryModel(id, name))
                    }
                    Log.d("Array Item", arrayItem.toString())
                    myAdapter!!.setData(arrayItem)

                    binding.productRecycleview.layoutManager = LinearLayoutManager(this@CategoryActivity)
                    binding.productRecycleview.adapter = myAdapter
                    progressDialog.dismiss()
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val searchItem = menu?.findItem(R.id.search)
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            searchView.maxWidth = Int.MAX_VALUE
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(filterString: String?): Boolean {
                    myAdapter!!.filter.filter(filterString)
                    return true
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shopping -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
//            R.id.transaction -> {
//                Intent(this@MainActivity, HistoryActivity::class.java).also {
//                    startActivities(arrayOf(it))
//                }
//                return true
//            }
            R.id.logout -> {
                sharedPref = getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.clear()
                editor.apply()

                val intent = Intent(this, LoginPegawaiActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "You have been Logout", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}