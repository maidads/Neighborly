package com.example.myneighborly

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HelpViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyPostsFragment()
            1 -> HelpNeededFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
