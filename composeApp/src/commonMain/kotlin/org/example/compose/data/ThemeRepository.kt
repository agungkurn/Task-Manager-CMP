package org.example.compose.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.compose.AppTheme

interface ThemeRepository {
    val currentTheme: Flow<AppTheme>
    val themes: List<AppTheme>

    suspend fun toggleTheme()
}

class ThemeRepositoryImpl(private val dataStore: DataStore<Preferences>) : ThemeRepository {
    private val themeKey = intPreferencesKey("theme")

    override val themes = listOf(AppTheme.System, AppTheme.Dark, AppTheme.Light)

    override val currentTheme: Flow<AppTheme>
        get() = dataStore.data.map { pref ->
            pref[themeKey]?.let { id ->
                themes.firstOrNull { id == it.id }
            } ?: themes.first()
        }

    override suspend fun toggleTheme() {
        dataStore.edit { pref ->
            pref[themeKey] = when (pref[themeKey]) {
                AppTheme.System.id -> AppTheme.Dark.id
                AppTheme.Dark.id -> AppTheme.Light.id
                AppTheme.Light.id -> AppTheme.System.id
                else -> AppTheme.Dark.id
            }
        }
    }
}