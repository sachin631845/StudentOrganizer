package com.example.studentorganizer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.studentorganizer.components.AppBar
import com.example.studentorganizer.models.Course

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentScreen(
    courses: List<Course>,
    onCancel: () -> Unit,
    onSave: (String, String, String, String, String, Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var course by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("Medium") }
    var description by remember { mutableStateOf("") }
    var reminder by remember { mutableStateOf(false) }
    var courseExpanded by remember { mutableStateOf(false) }
    val valid = title.isNotBlank() && course.isNotBlank() && dueDate.isNotBlank()

    Column(Modifier.fillMaxSize()) {
        AppBar("Add New Assignment", canNavigateBack = true, onBackClick = onCancel)
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("Create a task", color = Color.Gray, fontSize = 14.sp)
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Assignment Title *") }, singleLine = true, modifier = Modifier.fillMaxWidth(), leadingIcon = { Icon(Icons.Default.EditNote, contentDescription = null) })
            }
            item {
                ExposedDropdownMenuBox(expanded = courseExpanded, onExpandedChange = { courseExpanded = !courseExpanded }) {
                    OutlinedTextField(
                        value = course, onValueChange = { course = it }, label = { Text("Course Name / Code *") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = courseExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(), singleLine = true
                    )
                    ExposedDropdownMenu(expanded = courseExpanded, onDismissRequest = { courseExpanded = false }) {
                        courses.forEach { c ->
                            DropdownMenuItem(text = { Text("${c.name} (${c.code})") }, onClick = { course = c.name; courseExpanded = false })
                        }
                    }
                }
            }
            item {
                OutlinedTextField(value = dueDate, onValueChange = { dueDate = it }, label = { Text("Due Date * (e.g. Oct 15, 2026)") }, modifier = Modifier.fillMaxWidth(), singleLine = true, leadingIcon = { Icon(Icons.Default.CalendarMonth, contentDescription = null) })
            }
            item {
                Text("Priority Level", color = Navy, fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    listOf("Low", "Medium", "High").forEach { option ->
                        Row(Modifier.weight(1f).clickable { priority = option }, verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = priority == option, onClick = { priority = option })
                            Text(option, fontSize = 12.sp, color = Navy)
                        }
                    }
                }
            }
            item {
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description / Notes") }, modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 5)
            }
            item {
                Row(Modifier.fillMaxWidth().clickable { reminder = !reminder }, verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = reminder, onCheckedChange = { reminder = it })
                    Text("Enable Reminder Notification", color = Navy, fontSize = 14.sp)
                }
            }
            item { Text("* Required fields", color = Color.Gray, fontSize = 12.sp) }
        }
        Row(Modifier.fillMaxWidth().background(Color.White).padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onCancel, modifier = Modifier.weight(1f)) { Text("Cancel") }
            Button(
                onClick = { if (valid) onSave(title.trim(), course.trim(), dueDate.trim(), priority, description.trim(), reminder) },
                enabled = valid, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Blue)
            ) { Text("Save Assignment") }
        }
    }
}
