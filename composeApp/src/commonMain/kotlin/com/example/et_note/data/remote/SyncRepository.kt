package com.example.et_note.data.remote

import com.example.et_note.data.db.NoteDao
import com.example.et_note.data.db.SyncDataDao
import com.example.et_note.model.Note
import com.example.et_note.model.NoteChange
import com.example.et_note.model.SyncRequest
import com.example.et_note.model.SyncResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class SyncRepository(
    private val userId: String,
    private val noteDao: NoteDao,
    private val syncDataDao: SyncDataDao,
    private val apiService: ApiService
) {
    private val _syncState = MutableStateFlow<SyncState>(SyncState.Idle)
    val syncState = _syncState.asStateFlow()

    suspend fun performSync() = withContext(Dispatchers.IO){

        try {

            //        gather Sync Data
            val metaData = syncDataDao.getSyncMetaData()
            if (metaData?.isSyncing == true){
                return@withContext
            }

            _syncState.value = SyncState.Syncing
            syncDataDao.updateSyncingStatus(true)

//        fetch dirty notes from local database
            val dirtyNotes = noteDao.getDirtyNotes()
//        create SyncRequest object

            val syncRequest = SyncRequest(
                since = metaData?.lastSyncTimesStamp,
                changes = dirtyNotes.map { note ->
                    NoteChange(
                        id = note.id,
                        title = note.title,
                        body = note.description,
                        isDeleted = note.isDeleted,
                        updatedAt = note.updatedAt
                    )

                }
            )
            val response = apiService.sync(syncRequest)

//        process response

            response.getOrNull()?.let {
                processSyncResponse(it)
            }

            syncDataDao.updateLastSyncTimeStamp(response.getOrNull()?.nextSince ?: "")
            syncDataDao.updateSyncingStatus(false)

            _syncState.value = SyncState.Success(response.getOrNull()!!)

        }catch (ex: Exception){
            _syncState.value = SyncState.Error(ex.message ?: "An error occurred")
        }
    }


    suspend fun processSyncResponse(response: SyncResponse) = withContext(Dispatchers.IO) {
//        applied
        if(response.applied.isNotEmpty()){
            noteDao.markAsSynced(response.applied)
        }
//        conflicts
        if(response.conflicts.isNotEmpty()){
            val conflictsNotes = response.conflicts.map { noteChange ->
                Note(
                    title = noteChange.title,
                    description = noteChange.body,
                    id = noteChange.id,
                    isDeleted = noteChange.isDeleted,
                    updatedAt = noteChange.updatedAt,
                    isDirty = false,
                    userId = userId
                )

            }
            noteDao.insertNotes(conflictsNotes)
        }

//        changes

        if(response.changes.isNotEmpty()){
            val serverNotes = response.changes.map { noteChange ->
                Note(
                    title = noteChange.title,
                    description = noteChange.body,
                    id = noteChange.id,
                    isDeleted = noteChange.isDeleted,
                    updatedAt = noteChange.updatedAt,
                    isDirty = false,
                    userId = userId
                )

            }
            noteDao.insertNotes(serverNotes)
        }
    }
}

sealed class SyncState{
    object Idle: SyncState()
    object Syncing: SyncState()
    data class Success(val data: SyncResponse): SyncState()
    data class Error(val error: String): SyncState()
}