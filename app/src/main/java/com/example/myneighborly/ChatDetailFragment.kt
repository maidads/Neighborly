package com.example.myneighborly
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.ChatPreview
import com.example.myneighborly.ChatsAdapter
import com.example.myneighborly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatDetailFragment : Fragment() {

    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var recyclerView: RecyclerView
    private val messageList = mutableListOf<ChatMessage>()
    private lateinit var adapter: MessageAdapter
    private lateinit var chatId: String
    private val db = FirebaseFirestore.getInstance()
    private lateinit var currentUserId: String
    private lateinit var receiverId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_detail, container, false)


        messageInput = view.findViewById(R.id.messageInput)
        sendButton = view.findViewById(R.id.sendButton)
        recyclerView = view.findViewById(R.id.messageRecyclerView)
        chatId = arguments?.getString("chatId") ?: return view

        //val args = ChatDetailFragmentArgs.fromBundle(requireArguments())
        //chatId = args.chatId
        chatId = arguments?.getString("chatId") ?: ""
        if (chatId.isEmpty()) {
            Log.e("CHAT_DETAIL", "chatId saknas!")
            return view
        }
        Log.d("CHAT_DETAIL", "chatId från bundle: $chatId")
        currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return view

        adapter = MessageAdapter(messageList, currentUserId)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        val parts = chatId.split("-")
        receiverId = if (parts[0] == currentUserId) parts[1] else parts[0]

        val chatRef = db.collection("chats").document(chatId)
        chatRef.get().addOnSuccessListener { doc ->
            if (!doc.exists()) {
                val chatData = mapOf(
                    "participants" to listOf(currentUserId, receiverId),
                    "lastMessage" to "",
                    "timestamp" to System.currentTimeMillis()
                )
                chatRef.set(chatData)
            }
        }

        listenForMessages()
        sendButton.setOnClickListener { sendMessage() }

        return view
    }

    private fun listenForMessages() {
        db.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    Log.e("CHAT_DETAIL", "Kunde inte hämta meddelanden", error)
                    return@addSnapshotListener
                }
                messageList.clear()
                for (doc in snapshot.documents) {
                    val msg = doc.toObject(ChatMessage::class.java)
                    if (msg != null) {
                        messageList.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
                recyclerView.scrollToPosition(messageList.size - 1)
            }
    }

    private fun sendMessage() {
        val text = messageInput.text.toString().trim()
        if (text.isEmpty()) return

        val message = ChatMessage(
            senderId = currentUserId,
            senderName = "You",
            text = text,
            timestamp = System.currentTimeMillis()
        )

        db.collection("chats").document(chatId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener {
                messageInput.text.clear()
            }

        db.collection("chats").document(chatId)
            .update(mapOf(
                "lastMessage" to text,
                "timestamp" to System.currentTimeMillis()
            ))
    }
}
