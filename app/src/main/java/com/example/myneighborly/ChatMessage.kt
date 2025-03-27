package com.example.myneighborly

data class ChatMessage(
    val senderId: String = "",
    val senderName: String = "",
    val text: String = "",
    val timestamp: Long = 0L
)

