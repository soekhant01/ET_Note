package com.example.et_note

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.et_note.data.cache.createDataStore
import com.example.et_note.data.cache.dataStoreFileName
import androidx.datastore.preferences.core.Preferences

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    })