package com.example.et_note

import androidx.compose.ui.window.ComposeUIViewController
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.db.getNoteDatabase
import com.example.et_note.data.remote.createDataStore

fun MainViewController() = ComposeUIViewController {
    App(
        getNoteDatabase(getDatabaseBuilder()), DataStoreManager(createDataStore())
    )
}