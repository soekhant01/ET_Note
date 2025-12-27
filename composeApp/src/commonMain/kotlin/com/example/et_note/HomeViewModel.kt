package com.example.et_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.db.NoteDatabase
import com.example.et_note.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(noteDatabase: NoteDatabase, dataStoreManager: DataStoreManager) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    val userEmail = MutableStateFlow<String>("")
    init {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail()
            userEmail.value = email ?: ""
        }
    }


    fun addNotes(note: Note){
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }
}