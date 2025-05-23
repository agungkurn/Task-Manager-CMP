package org.example.compose.composables.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.compose.AppTheme
import org.example.compose.data.TaskRepository
import org.example.compose.data.ThemeRepository

class HomeViewModel(repository: TaskRepository, private val themeRepository: ThemeRepository) :
    ViewModel() {
    val tasks = repository.tasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val currentTheme = themeRepository.currentTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AppTheme.System)

    fun toggleTheme() {
        viewModelScope.launch {
            themeRepository.toggleTheme()
        }
    }
}