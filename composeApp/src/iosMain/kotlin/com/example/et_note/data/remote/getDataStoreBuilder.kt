package com.example.et_note.data.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.et_note.data.cache.createDataStore
import com.example.et_note.data.cache.dataStoreFileName
import com.example.et_note.data.db.NoteDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


fun getDataStoreBuilder(): DataStore<Preferences> {
    return createDataStore(
        producePath = {
            getDocumentPath() + "/${dataStoreFileName}"
        }
    )
}
@OptIn(ExperimentalForeignApi::class)
fun getDocumentPath(): String{

    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path)
}