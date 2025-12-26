package com.example.et_note

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.et_note.data.db.NoteDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask


fun getDatabaseBuilder(): RoomDatabase.Builder<NoteDatabase>{
    val dbPath = getDocument() + "/note_database.db"
    return Room.databaseBuilder(name = dbPath)
}

@OptIn(ExperimentalForeignApi::class)
fun getDocument(): String{

    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path)
}