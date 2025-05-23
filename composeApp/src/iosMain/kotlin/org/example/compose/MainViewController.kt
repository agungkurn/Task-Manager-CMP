package org.example.compose

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import org.example.compose.service.createDataStoreInstance

fun MainViewController() = ComposeUIViewController {
    val dataStore = remember { createDataStoreInstance() }

    App(dataStore = dataStore)
}