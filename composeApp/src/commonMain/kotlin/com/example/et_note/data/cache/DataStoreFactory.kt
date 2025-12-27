package com.example.et_note.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<java.util.prefs.Preferences> {
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {producePath().toPath()}
    )
}

internal const val dataStoreFileName = "etnotes.preferences.pb"