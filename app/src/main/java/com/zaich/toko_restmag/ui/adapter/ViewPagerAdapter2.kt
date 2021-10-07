package com.zaich.toko_restmag.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaich.toko_restmag.CreateUser
import com.zaich.toko_restmag.ListUser

class ViewPagerAdapter2(supportFragmentManager: FragmentManager, lifecycle: Lifecycle):
FragmentStateAdapter(supportFragmentManager,lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = ListUser()
            1 -> fragment = CreateUser()
        }
        return fragment as Fragment
    }
/*    private val aFragmentList = ArrayList<Fragment>()
    private val aFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return aFragmentList[position]
    }

    override fun getCount(): Int {
        return aFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return aFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment,title:String){
        aFragmentList.add(fragment)
        aFragmentTitleList.add(title)
    }*/
}