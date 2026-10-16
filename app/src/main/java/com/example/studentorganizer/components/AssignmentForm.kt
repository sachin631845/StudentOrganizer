package com.example.studentorganizer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentorganizer.models.Course

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentForm(
    title: String,
    onTitleChange: (String) -> Unit,
    course: String,
    onCourseChange: (String) -> Unit,
    dueDate: String,
    onDueDateChange: (String) -> Unit,
    priority: String,
    onPriorityChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    reminder: Boolean,
    onReminderChange: (Boolean) -> Unit,
    courses: List<Course>,
    modifier: Modifier = Modifier
) {
    // Dropdown expansion is local UI state; actual form values are hoisted to the caller.
    androidx.compose.runtime.key(courses.size) {
        varExpandedCourseDropdown(
            title = title, onTitleChange = onTitleChange,
            course = course, onCourseChange = onCourseChange,
            dueDate = dueDate, onDueDateChange = onDueDateChange,
            priority = priority, onPriorityChange = onPriorityChange,
            description = description, onDescriptionChange = onDescriptionChange,
            reminder = reminder, onReminderChange = onReminderChange,
            courses = courses, modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun varExpandedCourseDropdown(
    title: String, onTitleChange: (String) -> Unit,
    course: String, onCourseChange: (String) -> Unit,
    dueDate: String, onDueDateChange: (String) -> Unit,
    priority: String, onPriorityChange: (String) -> Unit,
    description: String, onDescriptionChange: (String) -> Unit,
    reminder: Boolean, onReminderChange: (Boolean) -> Unit,
    courses: List<Course>, modifier: Modifier
) {
    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Create a task", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        OutlinedTextField(
            value = title, onValueChange = onTitleChange,
            label = { Text("Assignment Title *") }, singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.EditNote, contentDescription = null) }
        )
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
            OutlinedTextField(
                value = course, onValueChange = onCourseChange,
                label = { Text("Course Name / Code *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor(), singleLine = true
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                courses.forEach { c ->
                    DropdownMenuItem(
                        text = { Text("${c.name} (${c.code})") },
                        onClick = { onCourseChange(c.name); expanded = false }
                    )
                }
            }
        }
        OutlinedTextField(
            value = dueDate, onValueChange = onDueDateChange,
            label = { Text("Due Date * (e.g. Oct 15, 2026)") },
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) }
        )
        Text("Priority Level", color = MaterialTheme.colorScheme.onSurface)
        Row(verticalAlignment = Alignment.CenterVertically) {
            listOf("Low", "Medium", "High").forEach { option ->
                Row(
                    Modifier.weight(1f).clickable { onPriorityChange(option) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = priority == option, onClick = { onPriorityChange(option) })
                    Text(option, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
        OutlinedTextField(
            value = description, onValueChange = onDescriptionChange,
            label = { Text("Description / Notes") },
            modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5
        )
        Row(
            Modifier.fillMaxWidth().clickable { onReminderChange(!reminder) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = reminder, onCheckedChange = onReminderChange)
            Text("Enable Reminder Notification", color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp)
        }
        Text("* Required fields", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
    }
}
