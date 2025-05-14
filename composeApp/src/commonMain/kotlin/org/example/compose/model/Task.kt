package org.example.compose.model

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val status: TaskStatus,
    val created: Long,
    val lastUpdated: Long
)
