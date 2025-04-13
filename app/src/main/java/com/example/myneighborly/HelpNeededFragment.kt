package com.example.myneighborly

import HelpAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HelpNeededFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val helpRequests = mutableListOf<HelpRequest>()
    private lateinit var adapter: HelpAdapter
    private val db = FirebaseFirestore.getInstance()
    private val currentUserId: String? get() = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_help_needed, container, false)
        recyclerView = view.findViewById(R.id.helpRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = HelpAdapter(helpRequests) { helpRequest ->
            val myId = currentUserId
            Log.d("HELP_DEBUG", "Klickade på hjälp: $helpRequest")

            if (myId == null || helpRequest.userId.isBlank()) {
                Log.w("HELP_DEBUG", "currentUserId eller userId saknas!")
                return@HelpAdapter
            }

            val chatId = generateChatId(myId, helpRequest.userId)
            Log.d("HELP_DEBUG", "Genererat chatId: $chatId")

            val action = HelpNeededFragmentDirections
                .actionHelpNeededFragmentToChatDetailFragment(chatId)

            findNavController().navigate(action)
        }


        recyclerView.adapter = adapter

        loadHelpRequests()

        return view
    }

    private fun loadHelpRequests() {
        Log.d("HELP_DEBUG", "Laddar hjälp-förfrågningar...")
        db.collectionGroup("helpRequests")
            .get()
            .addOnSuccessListener { result ->
                Log.d("HELP_DEBUG", "Hämtade ${result.size()} förfrågningar")
                helpRequests.clear()
                for (doc in result) {
                    val help = doc.toObject(HelpRequest::class.java)
                    Log.d("HELP_DEBUG", "Förfrågan: $help")
                    helpRequests.add(help)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.e("HELP_DEBUG", "Fel vid hämtning: ", e)
            }
    }


    private fun generateChatId(user1: String, user2: String): String {
        return if (user1 < user2) {
            "$user1-$user2"
        } else {
            "$user2-$user1"
        }
    }
}
