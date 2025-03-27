package com.example.myneighborly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val requestHelpText: TextView = rootView.findViewById(R.id.requestHelpText)

        requestHelpText.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, AddHelpFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val recyclerView: RecyclerView = rootView.findViewById(R.id.helpTypesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = HelpTypesAdapter(getHelpTypes())

        val tabLayout: TabLayout = rootView.findViewById(R.id.tabLayout)
        val viewPager: ViewPager2 = rootView.findViewById(R.id.viewPager)

        val adapter = HelpViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "My posts"
                1 -> "Help needed"
                else -> ""
            }
        }.attach()

        return rootView
    }

    private fun getHelpTypes(): List<HelpType> {
        return listOf(
            HelpType(R.drawable.study, "Homework"),
            HelpType(R.drawable.childcare, "Childcare"),
            HelpType(R.drawable.mail, "Mail Pickup"),
            HelpType(R.drawable.moving, "Moving"),
            HelpType(R.drawable.shop, "Shop")
        )
    }
}
