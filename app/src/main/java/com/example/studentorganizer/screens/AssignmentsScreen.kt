
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

private val Blue = Color(0xFF356AE6)

@Composable
fun AssignmentsScreen(
    assignments: List<Assignment>,
    onToggle: (Int) -> Unit
) {
    var filter by remember { mutableStateOf("All") }

    val filtered = assignments.filter { assignment ->
        when (filter) {
            "Pending" -> !assignment.completed
            "Completed" -> assignment.completed
            else -> true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppBar("My Assignments")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("All", "Pending", "Completed").forEach { option ->
                Row(
                    modifier = Modifier
                        .clickable { filter = option }
                        .padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = filter == option,
                        onClick = { filter = option },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Blue
                        )
                    )
                    Text(
                        text = option,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No assignments in this filter.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 100.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = filtered,
                    key = { it.id }
                ) { assignment ->
                    AssignmentCard(
                        assignment.title,
                        assignment.course,
                        assignment.dueDate,
                        assignment.priority,
                        assignment.completed
                    ) {
                        onToggle(assignment.id)
                    }
                }
            }
        }
    }
}
