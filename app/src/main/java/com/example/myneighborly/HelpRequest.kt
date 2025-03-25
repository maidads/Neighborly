package com.example.myneighborly

data class HelpRequest(
    var id: String = "",
    val category: String = "",
    val address: String = "",
    val type: String = "",
    val details: String = "",
    val userId: String = ""
)
