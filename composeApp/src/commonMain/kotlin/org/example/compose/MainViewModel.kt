package org.example.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.example.compose.data.ThemeRepository

class MainViewModel(themeRepository: ThemeRepository) : ViewModel() {
    val currentTheme = themeRepository.currentTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AppTheme.System)
}