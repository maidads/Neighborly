package com.example.myneighborly

import com.example.myneighborly.adapter.HelpAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    private lateinit var searchBar: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HelpAdapter
    private val allAds = mutableListOf<HelpRequest>()
    private val filteredAds = mutableListOf<HelpRequest>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        searchBar = rootView.findViewById(R.id.searchBar)

        recyclerView = rootView.findViewById(R.id.adsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HelpAdapter(filteredAds) { help ->
            Toast.makeText(requireContext(), "Klickat: ${help.category}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        loadAds()

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                filteredAds.clear()
                filteredAds.addAll(allAds.filter {
                    it.category.lowercase().contains(query) || it.type.lowercase().contains(query)
                })
                adapter.notifyDataSetChanged()
            }
        })
        return rootView
    }
    private fun loadAds() {
        FirebaseFirestore.getInstance().collectionGroup("helpRequests")
            .get()
            .addOnSuccessListener { result ->
                allAds.clear()
                for (doc in result) {
                    val help = doc.toObject(HelpRequest::class.java)
                    allAds.add(help)
                }
                filteredAds.clear()
                filteredAds.addAll(allAds)
                adapter.notifyDataSetChanged()
            }
    }

}
