package com.khtn.uaieo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khtn.uaieo.fragment.AnalystFragment
import com.khtn.uaieo.fragment.ExamFragment
import com.khtn.uaieo.fragment.HomeFragment
import com.khtn.uaieo.fragment.ProfileFragment

class TabPageAdapter(activity: FragmentActivity,private val tabCount:Int) :FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->HomeFragment()
            1->ExamFragment()
            2->AnalystFragment()
            3->ProfileFragment()
            else->HomeFragment()
        }
    }

}