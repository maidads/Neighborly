package com.example.myneighborly
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.ChatPreview
import com.example.myneighborly.ChatsAdapter
import com.example.myneighborly.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageAdapter(
    private val messages: List<ChatMessage>,
    private val currentUserId: String
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val senderName: TextView = view.findViewById(R.id.senderName)
        val messageText: TextView = view.findViewById(R.id.messageText)
        val messageTime: TextView = view.findViewById(R.id.messageTime)
        val profileImage: ImageView? = view.findViewById(R.id.profileImage)
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].senderId == currentUserId) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layout = if (viewType == 1)
            R.layout.item_message_sent else R.layout.item_message_received
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.messageText.text = message.text
        holder.senderName.text = if (message.senderId == currentUserId) "You" else message.senderName
        holder.messageTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp))
    }

    override fun getItemCount(): Int = messages.size
}
