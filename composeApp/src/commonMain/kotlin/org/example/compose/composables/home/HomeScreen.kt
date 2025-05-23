package org.example.compose.composables.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoMode
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.compose.AppTheme
import org.example.compose.model.Task
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateTask: () -> Unit,
    onOpenTask: (Task) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    val currentTheme by viewModel.currentTheme.collectAsState()

    val icon = remember(currentTheme) {
        when (currentTheme) {
            AppTheme.System -> Icons.Default.AutoMode
            AppTheme.Dark -> Icons.Default.DarkMode
            AppTheme.Light -> Icons.Default.LightMode
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    IconButton(onClick = viewModel::toggleTheme) {
                        Icon(icon, contentDescription = stringResource(currentTheme.stringResource))
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateTask) {
                Icon(Icons.Default.Add, contentDescription = "create new task")
            }
        }
    ) {
        AnimatedContent(tasks.isNotEmpty()) { isNotEmpty ->
            if (isNotEmpty) {
                LazyColumn(
                    modifier = Modifier
                        .padding(it)
                        .consumeWindowInsets(it)
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(tasks) { task ->
                        TaskItem(
                            modifier = Modifier.fillParentMaxWidth(),
                            task = task,
                            onClick = { onOpenTask(task) }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .padding(it)
                        .consumeWindowInsets(it)
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tasks yet")
                }
            }
        }
    }
}

@Composable
private fun TaskItem(task: Task, onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(task.status.color, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Text(
                    text = task.status.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    softWrap = true,
                    overflow = TextOverflow.Ellipsis,
                    color = contentColorFor(task.status.color)
                )
            }
        }
    }
}