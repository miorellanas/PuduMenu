package com.example.pudumenu.data.model

data class User(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String // Storing password hash instead of plain text
)
