package org.example.compose.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun createDataStoreInstance(context: Context): DataStore<Preferences> {
    return createDataStoreFile {
        context.filesDir.resolve(dataStoreFileName).absolutePath
    }
}