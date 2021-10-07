package com.zaich.toko_restmag.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.zaich.toko_restmag.R
import com.zaich.toko_restmag.databinding.ActivityUserBinding
import com.zaich.toko_restmag.ui.adapter.ViewPagerAdapter2

class UserActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navDrawerView: NavigationView
    private lateinit var binding: ActivityUserBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewPagerAdapter = ViewPagerAdapter2(supportFragmentManager, lifecycle)
        with(binding) {
            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLE[position])
            }.attach()
        }
    }
//        setUpTabs()


//    private fun setUpTabs(){
//        val adapter = ViewPagerAdapter(supportFragmentManager)
//        adapter.addFragment(ListUser(),"List")
//        adapter.addFragment(CreateUser(),"create")
//        binding.viewPager.adapter = adapter
//        binding.tabMode.setupWithViewPager(binding.viewPager)
//    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
//        const val EXTRA_USER = "EXTRA USER"

        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_3, R.string.tab_4
        )
    }
}