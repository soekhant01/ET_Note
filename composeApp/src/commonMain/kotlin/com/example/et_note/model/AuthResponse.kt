package com.example.et_note.model

data class AuthResponse(
    val accessToken: String,
    val email: String,
    val refreshToken: String,
    val userId: String
)