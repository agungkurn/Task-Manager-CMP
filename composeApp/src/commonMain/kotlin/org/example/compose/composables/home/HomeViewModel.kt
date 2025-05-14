package org.example.compose.composables.home

import androidx.lifecycle.ViewModel
import org.example.compose.data.TaskRepository

class HomeViewModel(repository: TaskRepository) : ViewModel() {
    val tasks = repository.tasks
}