package com.example.myneighborly

data class ChatPreview(
    var chatId: String = "",
    val participants: List<String>  = listOf(),
    val lastMessage: String = "",
    val timestamp: Long = 0L
)
