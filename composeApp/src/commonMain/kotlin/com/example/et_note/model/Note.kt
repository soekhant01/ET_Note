package com.example.et_note.model

import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "note")
data class Note @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: String = Uuid.random().toString(),
    val title: String,
    val description: String,
    val isDeleted: Boolean = false,
    val isDirty: Boolean = false,
    val userId: String,
    val updatedAt: String = Clock.System.now().toString(),
)


@Entity
data class SyncMetaData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastSyncTimesStamp: String? = null,
    val isSyncing: Boolean = false
)