package com.example.myneighborly
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.ChatPreview
import com.example.myneighborly.ChatsAdapter
import com.example.myneighborly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ChatsAdapter
    private val chatList = mutableListOf<ChatPreview>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chats, container, false)
        recyclerView = view.findViewById(R.id.chatsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ChatsAdapter(chatList) { selectedChat ->
            val action = ChatsFragmentDirections
                .actionChatsFragmentToChatDetailFragment()
            findNavController().navigate(action)
        }
        recyclerView.adapter = adapter

        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return view
        val db = FirebaseFirestore.getInstance()
        val testChatId = "test-$currentUserId"
        val chatRef = db.collection("chats").document(testChatId)

        val testChat = mapOf(
            "participants" to listOf(currentUserId, currentUserId),
            "lastMessage" to "Testchatt 🎯",
            "timestamp" to System.currentTimeMillis()
        )

        chatRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val testChat = mapOf(
                    "participants" to listOf(currentUserId, currentUserId),
                    "lastMessage" to "Testchatt 🎯",
                    "timestamp" to System.currentTimeMillis()
                )

                chatRef.set(testChat).addOnSuccessListener {
                    val testMessage = mapOf(
                        "senderId" to currentUserId,
                        "text" to "Hej från testmeddelande",
                        "timestamp" to System.currentTimeMillis()
                    )

                    chatRef.collection("messages").add(testMessage)
                        .addOnSuccessListener {
                            loadChats()
                        }
                }
            } else {
                loadChats()
            }
        }

        return view
    }


    private fun loadChats() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        db.collection("chats")
            .whereArrayContains("participants", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("CHAT_DEBUG", "Snapshot error", error)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    chatList.clear()
                    for (doc in snapshot) {
                        val chat = doc.toObject(ChatPreview::class.java)
                        chat.chatId = doc.id
                        chatList.add(chat)
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    Log.d("CHAT_DEBUG", "Tom snapshot – inga chattar")
                    chatList.clear()
                    adapter.notifyDataSetChanged()
                }
            }
    }

}