package com.example.dungeonans.PageAdapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dungeonans.Fragment.AskFragment
import com.example.dungeonans.Fragment.BlogFragment
import com.example.dungeonans.Fragment.CommunityFragment
import com.example.dungeonans.Fragment.MyProfileFragment

class ViewPagerAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int = 4
    // 뷰페이저 어댑터

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlogFragment()
            1 -> AskFragment()
            2 -> CommunityFragment()
            else -> MyProfileFragment()
        }
    }
}