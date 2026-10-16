package com.example.studentorganizer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.studentorganizer.components.AppBar
import com.example.studentorganizer.components.AssignmentForm
import com.example.studentorganizer.models.Course

@Composable
fun AddAssignmentScreen(
    courses: List<Course>,
    onCancel: () -> Unit,
    onSave: (String, String, String, String, String, Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Low") }
    var description by remember { mutableStateOf("") }
    var reminder by remember { mutableStateOf(false) }
    val valid = title.isNotBlank() && course.isNotBlank() && dueDate.isNotBlank()

    Column(Modifier.fillMaxSize()) {
        AppBar("Add New Assignment", canNavigateBack = true, onBackClick = onCancel)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        ) {
            item {
                AssignmentForm(
                    title = title, onTitleChange = { title = it },
                    course = course, onCourseChange = { course = it },
                    dueDate = dueDate, onDueDateChange = { dueDate = it },
                    priority = priority, onPriorityChange = { priority = it },
                    description = description, onDescriptionChange = { description = it },
                    reminder = reminder, onReminderChange = { reminder = it },
                    courses = courses
                )
            }
        }
        Surface(color = MaterialTheme.colorScheme.surface, tonalElevation = 2.dp) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(
                    onClick = {
                        if (valid) onSave(title.trim(), course.trim(), dueDate.trim(), priority, description.trim(), reminder)
                    },
                    enabled = valid, modifier = Modifier.weight(1f)
                ) { Text("Save Assignment") }
            }
        }
    }
}
