package com.example.myneighborly

data class HelpRequest(
    var id: String = "",
    val category: String = "",
    val address: String = "",
    val date: String = "",
    val details: String = "",
    val userId: String = ""
)
