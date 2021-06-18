package com.restmag.resmag

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.restmag.resmag.adapter.ViewPagerAdapter
import com.restmag.resmag.fragment.CreateUserFragment
import com.restmag.resmag.fragment.ListUserFragment
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navDrawerView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setUpTabs()
    }

    private fun setUpTabs(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ListUserFragment(),"List")
        adapter.addFragment(CreateUserFragment(),"create")
        viewPager.adapter = adapter
        tabMode.setupWithViewPager(viewPager)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}