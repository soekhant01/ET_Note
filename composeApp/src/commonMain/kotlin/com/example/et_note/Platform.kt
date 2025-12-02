package com.example.et_note

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform