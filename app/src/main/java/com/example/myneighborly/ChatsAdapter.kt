package com.example.myneighborly
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatsAdapter(
    private val chats: List<ChatPreview>,
    private val onChatClick: (ChatPreview) -> Unit
) : RecyclerView.Adapter<ChatsAdapter.ChatViewHolder>() {

    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val lastMessage: TextView = view.findViewById(R.id.lastMessage)
        val userName: TextView = view.findViewById(R.id.userName)

        init {
            view.setOnClickListener {
                onChatClick(chats[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_preview_item, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chats[position]
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        val otherUser = chat.participants.find { it != currentUserId }
        holder.userName.text = otherUser ?: "You"
        holder.lastMessage.text = chat.lastMessage
    }

    override fun getItemCount(): Int = chats.size
}
