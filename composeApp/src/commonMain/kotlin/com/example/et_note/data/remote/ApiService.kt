package com.example.et_note.data.remote

import com.example.et_note.model.AuthRequest
import com.example.et_note.model.AuthResponse

expect val BASE_URL_EMULATOR: String
const val BASE_URL = "https://localhost:8080"

class ApiService {

    fun login(request: AuthRequest): Result<AuthResponse>{

    }
}