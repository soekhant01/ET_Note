package com.example.et_note.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.et_note.model.Note
import com.example.et_note.model.SyncMetaData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [Note::class, SyncMetaData::class], version = 1, exportSchema = true)
@ConstructedBy(NoteDatabaseConstructor::class )
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun syncMetaDataDao(): SyncDataDao
}

@Suppress("KotlinNoActualForExpect")
expect object NoteDatabaseConstructor: RoomDatabaseConstructor<NoteDatabase>{
    override fun initialize(): NoteDatabase
}

fun getNoteDatabase(builder: RoomDatabase.Builder<NoteDatabase>): NoteDatabase{

    return builder
        .setDriver(BundledSQLiteDriver( ))
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}