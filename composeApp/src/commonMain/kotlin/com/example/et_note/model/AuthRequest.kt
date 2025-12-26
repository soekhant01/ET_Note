package com.example.et_note.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val email: String, val password: String
)