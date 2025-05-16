package org.example.compose.composables.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.example.compose.data.TaskRepository
import org.example.compose.model.TaskStatus

class CreateTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _selectedStatus = MutableStateFlow(TaskStatus.ToDo)
    val selectedStatus = _selectedStatus.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    val enableSave = combine(_title, _description) { newTitle, newDescription ->
        newTitle.trim().isNotBlank() && newDescription.trim().isNotBlank()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun onTitleChange(value: String) {
        _title.value = value
    }

    fun onDescriptionChange(value: String) {
        _description.value = value
    }

    fun onSelectedStatusChange(value: TaskStatus) {
        _selectedStatus.value = value
    }

    fun submit() {
        repository.addTask(
            title = _title.value,
            description = _description.value,
            status = _selectedStatus.value
        )
        _saved.value = true
    }
}