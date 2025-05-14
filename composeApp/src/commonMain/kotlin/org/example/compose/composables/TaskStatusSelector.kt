package org.example.compose.composables

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.fastForEachIndexed
import org.example.compose.model.TaskStatus

@Composable
fun TaskStatusSelector(
    selectedStatus: TaskStatus?,
    onStatusSelected: (TaskStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val taskStatus = remember { TaskStatus.entries.toList() }

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        taskStatus.fastForEachIndexed { i, status ->
            SegmentedButton(
                selected = status == selectedStatus,
                onClick = { onStatusSelected(status) },
                shape = SegmentedButtonDefaults.itemShape(i, taskStatus.size),
            ) {
                Text(text = status.displayName)
            }
        }
    }
}