package com.example.studentorganizer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studentorganizer.components.AppBar
import com.example.studentorganizer.components.AssignmentCard
import com.example.studentorganizer.models.Assignment

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)

@Composable
fun AssignmentsScreen(assignments: List<Assignment>, onToggle: (Int) -> Unit) {
    var filter by remember { mutableStateOf("All") }
    val filtered = assignments.filter {
        when (filter) { "Pending" -> !it.completed; "Completed" -> it.completed; else -> true }
    }
    Column(Modifier.fillMaxSize()) {
        AppBar("My Assignments")
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            listOf("All", "Pending", "Completed").forEach { option ->
                Row(Modifier.clickable { filter = option }.padding(end = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = filter == option, onClick = { filter = option }, colors = RadioButtonDefaults.colors(selectedColor = Blue))
                    Text(option, color = Navy)
                }
            }
        }
        if (filtered.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("No assignments in this filter.", color = Color.Gray) }
        } else {
            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
                items(filtered, key = { it.id }) { a ->
                    AssignmentCard(a.title, a.course, a.dueDate, a.priority, a.completed) { onToggle(a.id) }
                }
            }
        }
    }
}
