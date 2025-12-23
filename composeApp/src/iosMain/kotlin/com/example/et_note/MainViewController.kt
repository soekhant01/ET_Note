package com.example.et_note

import androidx.compose.ui.window.ComposeUIViewController
import com.example.et_note.db.getNoteDatabase

fun MainViewController() = ComposeUIViewController { App(
    getNoteDatabase(getDatabaseBuilder())
) }