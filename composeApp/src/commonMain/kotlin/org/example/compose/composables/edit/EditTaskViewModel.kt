package org.example.compose.composables.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.compose.data.TaskRepository
import org.example.compose.model.Task
import org.example.compose.model.TaskStatus

class EditTaskViewModel(private val repository: TaskRepository) : ViewModel() {
    private val savedTask = MutableStateFlow<Task?>(null)

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _selectedStatus = MutableStateFlow(TaskStatus.ToDo)
    val selectedStatus = _selectedStatus.asStateFlow()

    private val _saved = MutableStateFlow(false)
    val saved = _saved.asStateFlow()

    val enableSave =
        combine(
            _title,
            _description,
            _selectedStatus,
            savedTask
        ) { newTitle, newDescription, newStatus, savedTask ->
            (newTitle.trim().isNotBlank() && newTitle.trim() != savedTask?.title)
                    || (newDescription.trim()
                .isNotBlank() && newDescription.trim() != savedTask?.description)
                    || newStatus != savedTask?.status
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    fun setInitialData(taskId: Long) {
        viewModelScope.launch {
            repository.getTaskByIdAsOneShot(taskId)?.let { task ->
                savedTask.value = task

                _title.value = task.title
                _description.value = task.description
                _selectedStatus.value = task.status
            }
        }
    }

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
        viewModelScope.launch {
            savedTask.value?.let {
                repository.updateTask(
                    id = it.id,
                    title = _title.value,
                    description = _description.value,
                    status = _selectedStatus.value
                )
                _saved.value = true
            }
        }
    }
}