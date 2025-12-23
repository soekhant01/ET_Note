package com.example.et_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.et_note.db.NoteDatabase
import com.example.et_note.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(noteDatabase: NoteDatabase) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    fun addNotes(note: Note){
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }
}