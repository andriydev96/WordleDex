package com.example.wordledex.achievements

data class Achievement(
    val name : String,
    val description: String,
    val icon: Int,
    var got: Boolean = false
)
