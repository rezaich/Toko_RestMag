package com.zaich.toko_restmag.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.zaich.toko_restmag.CreateUser
import com.zaich.toko_restmag.ListUser
import com.zaich.toko_restmag.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navDrawerView: NavigationView
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListUser(),"List")
        adapter.addFragment(CreateUser(),"create")
        binding.viewPager.adapter = adapter
        binding.tabMode.setupWithViewPager(binding.viewPager)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}