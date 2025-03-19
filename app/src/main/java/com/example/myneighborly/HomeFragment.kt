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

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val requestHelpText: TextView = rootView.findViewById(R.id.requestHelpText)

        requestHelpText.setOnClickListener {
            //findNavController().navigate(R.id.action_homeFragment_to_addHelpFragment)
        }

        val recyclerView: RecyclerView = rootView.findViewById(R.id.helpTypesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = HelpTypesAdapter(getHelpTypes())

        return rootView
    }

    private fun getHelpTypes(): List<HelpType> {
        return listOf(
            HelpType(R.drawable.study, "Homework"),
            HelpType(R.drawable.study, "Childcare"),
            HelpType(R.drawable.study, "Mail Pickup"),
            HelpType(R.drawable.study, "Moving"),
            HelpType(R.drawable.study, "Shop")
        )
    }
}
