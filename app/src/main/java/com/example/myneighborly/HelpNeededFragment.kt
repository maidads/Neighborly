package com.example.myneighborly

import HelpAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class HelpNeededFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val helpRequests = mutableListOf<HelpRequest>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help_needed, container, false)
        recyclerView = view.findViewById(R.id.helpRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadHelpRequests()

        return view
    }

    private fun loadHelpRequests() {
        val db = FirebaseFirestore.getInstance()
        db.collectionGroup("helpRequests")
            .get()
            .addOnSuccessListener { result ->
                helpRequests.clear()
                for (doc in result) {
                    val help = doc.toObject(HelpRequest::class.java)
                    helpRequests.add(help)
                }

                recyclerView.adapter = HelpAdapter(helpRequests) { helpRequest ->
                    Toast.makeText(requireContext(), "Start chat with ${helpRequest.userId}", Toast.LENGTH_SHORT).show()
                    // findNavController().navigate(...) <- chat fragment
                }
            }
    }
}
