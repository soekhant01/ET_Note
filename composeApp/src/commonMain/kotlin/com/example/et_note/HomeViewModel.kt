package com.example.et_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.et_note.data.cache.DataStoreManager
import com.example.et_note.data.db.NoteDatabase
import com.example.et_note.data.remote.ApiService
import com.example.et_note.data.remote.HttpClientFactory
import com.example.et_note.data.remote.SyncRepository
import com.example.et_note.data.remote.SyncState
import com.example.et_note.model.Note
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(val noteDatabase: NoteDatabase, val dataStoreManager: DataStoreManager) : ViewModel() {

    private val dao = noteDatabase.noteDao()
    private val _notes = dao.getAllNotes()
    val notes = _notes

    val userEmail = MutableStateFlow<String>("")
    init {
        viewModelScope.launch {
            val email = dataStoreManager.getEmail()
            userEmail.value = email ?: ""
            performSync()
        }
    }

    fun performSync() {
        viewModelScope.launch {
            val apiService = ApiService(HttpClientFactory.getHttpClient(), dataStoreManager)
            val userId = dataStoreManager.getUserId() ?: return@launch
            val syncRepository = SyncRepository(
                userId = userId,
                noteDao = noteDatabase.noteDao(),
                syncDataDao = noteDatabase.syncMetaDataDao(),
                apiService = apiService
            )

            syncRepository.performSync()

            syncRepository.syncState.collectLatest {
                when(it){
                    is SyncState.Syncing -> {}
                    is SyncState.Idle -> {}
                    is SyncState.Error -> {}
                    is SyncState.Success -> {}
                }
            }
        }


    }


    fun addNotes(note: Note){
        viewModelScope.launch {
            dao.insertNotes(note)
            performSync()
        }
    }
}