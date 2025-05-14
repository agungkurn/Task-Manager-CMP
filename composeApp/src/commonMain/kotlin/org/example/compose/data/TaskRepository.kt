package org.example.compose.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.example.compose.model.Task
import org.example.compose.model.TaskStatus
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object TaskRepository {
    private val _tasks = MutableStateFlow(listOf<Task>())
    val tasks = _tasks.asStateFlow()

    fun getTaskByIdAsFlow(id: Long) = tasks.map { it.firstOrNull { task -> task.id == id } }

    fun getTaskByIdAsOneShot(id: Long): Task? {
        return _tasks.value.firstOrNull { it.id == id }
    }

    @OptIn(ExperimentalTime::class)
    fun addTask(title: String, description: String, status: TaskStatus) {
        val now = Clock.System.now().toEpochMilliseconds()
        val newTask = Task(
            id = now,
            title = title,
            description = description,
            status = status,
            created = now,
            lastUpdated = now
        )
        _tasks.update { it + newTask }
    }

    @OptIn(ExperimentalTime::class)
    fun updateTask(id: Long, title: String, description: String, status: TaskStatus) {
        val now = Clock.System.now().epochSeconds
        _tasks.update {
            it.map { task ->
                if (task.id == id) {
                    task.copy(
                        title = title,
                        description = description,
                        status = status,
                        lastUpdated = now
                    )
                } else {
                    task
                }
            }
        }
    }

    fun deleteTask(task: Task) {
        _tasks.update { it.filterNot { it.id == task.id } }
    }

    fun changeStatus(id: Long, taskStatus: TaskStatus) {
        _tasks.update {
            it.map { task ->
                if (task.id == id) {
                    task.copy(status = taskStatus)
                } else {
                    task
                }
            }
        }
    }
}