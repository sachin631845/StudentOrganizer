package com.example.studentorganizer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.studentorganizer.components.AssignmentCard
import com.example.studentorganizer.components.CourseInfo
import com.example.studentorganizer.models.Assignment
import com.example.studentorganizer.models.Course

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)
private val PaleBlue = Color(0xFFEAF0FF)

@Composable
fun DashboardScreen(
    studentName: String,
    courses: List<Course>,
    assignments: List<Assignment>,
    onToggle: (Int) -> Unit,
    onViewAssignments: () -> Unit
) {
    //it composes list items as needed, which is useful when displaying many items.
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text("Welcome back,", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                Text(if (studentName.isBlank()) "Student 👋" else "$studentName 👋", color = MaterialTheme.colorScheme.onSurface, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Here's your learning overview.", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Active Courses", courses.size.toString(), Icons.Default.MenuBook, Modifier.weight(1f))
                    StatCard("Pending Tasks", assignments.count { !it.completed }.toString(), Icons.Default.Assignment, Modifier.weight(1f))
                }
                Spacer(Modifier.height(24.dp))
                SectionHeading("Active Courses", "View all", onViewAssignments)
            }
        }
        item { //horizontal scrolling list
            LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(courses) { course ->
                    CourseInfo(course.name, course.code, course.instructor, course.progress, onClick = onViewAssignments)
                }
            }
        }
        item {
            Column(Modifier.padding(horizontal = 20.dp).padding(top = 24.dp)) {
                SectionHeading("Urgent Deadlines", "View all", onViewAssignments)
            }
        }
        if (assignments.none { !it.completed }) {
            item {
                Text("You're all caught up. Add an assignment with the + button.",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp), color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        items(assignments.filter { !it.completed }.take(3), key = { it.id }) { assignment ->
            Box(Modifier.padding(horizontal = 20.dp)) {
                AssignmentCard(assignment.title, assignment.course, assignment.dueDate, assignment.priority, assignment.completed) {
                    onToggle(assignment.id)
                }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Blue)
            Spacer(Modifier.height(10.dp))
            Text(value, fontSize = 27.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
            Text(title, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SectionHeading(title: String, trailing: String, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(trailing, color = Blue, fontSize = 12.sp, modifier = Modifier.clickable(onClick = onClick).padding(6.dp))
    }
}
