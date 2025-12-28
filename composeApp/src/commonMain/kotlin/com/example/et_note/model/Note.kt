package com.example.et_note.model

import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity
data class Note @OptIn(ExperimentalUuidApi::class) constructor(
    val title: String, val description: String,
    @PrimaryKey(autoGenerate = true) val id: String = Uuid.random().toString(),
    val isDeleted: Boolean = false,
    val updatedAt: String,
    val isDirty: Boolean = false,
    val userId: String
)


@Entity
data class SyncMetaData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastSyncTimesStamp: String? = null,
    val isSyncing: Boolean = false
)