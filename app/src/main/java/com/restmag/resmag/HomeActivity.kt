package com.restmag.resmag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.restmag.resmag.fragment.AkunFragment
import com.restmag.resmag.fragment.HomeFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navDrawerView: NavigationView

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //call findByid on the drawerLayout
        drawerLayout = findViewById(R.id.activity_home)

        //pass the actionToggle action into the drawerlistener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // display the hamburger icon to lunch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // call syncState() on actiom bar so it'll automatically
        //change to the back button when the drawer layout is open
        actionBarToggle.syncState()

        navDrawerView = findViewById(R.id.navDrawer)

        navDrawerView.setNavigationItemSelectedListener { menuitem ->
            when (menuitem.itemId) {
                R.id.menuHome -> {
                    startActivity(Intent(application, MainActivity::class.java))
                    true
                }
                R.id.userManagement -> {
                    startActivity(Intent(application, UserActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }
        }

        //initilisasi fragment
        val homeFragment = HomeFragment()
        val akunFragment = AkunFragment()
        setCurrentFragment(homeFragment)

        //Call Bottom navigation
        bottomNavigation = findViewById(R.id.navBottom)
        //running bottom navigation
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(homeFragment)
                R.id.navigation_account -> setCurrentFragment(akunFragment)
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            this.drawerLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}