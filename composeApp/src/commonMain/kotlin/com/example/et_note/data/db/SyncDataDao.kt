package com.example.et_note.data.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.et_note.model.SyncMetaData

interface SyncDataDao {

    @Query("SELECT * FROM syncmetadata WHERE id = 1")
    suspend fun getSyncMetaData(): SyncMetaData?

    @Query("UPDATE syncmetadata SET lastSyncTimesStamp = :timestamp WHERE id = 1")
    suspend fun updateLastSyncTimeStamp(timestamp: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSyncMetadata(metaData: SyncMetaData)

    @Query("UPDATE syncmetadata SET isSyncing = :isSyncing WHERE id = 1")
    suspend fun updateSyncingStatus(isSyncing: Boolean)
}