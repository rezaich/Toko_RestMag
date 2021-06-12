package com.zaich.toko_restmag

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager):
FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val aFragmentList = ArrayList<Fragment>()
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
    }
}