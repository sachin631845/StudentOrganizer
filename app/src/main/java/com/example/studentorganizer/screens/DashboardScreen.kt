package com.example.studentorganizer.screens

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
fun DashboardScreen(courses: List<Course>, assignments: List<Assignment>, onToggle: (Int) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)) {
        item {
            Column(Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                Text("Welcome back,", color = Color.Gray, fontSize = 14.sp)
                Text("Sachin! 👋", color = Navy, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text("Here's your learning overview.", color = Color.Gray, fontSize = 14.sp)
                Spacer(Modifier.height(20.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatCard("Active Courses", "4", Icons.Default.MenuBook, Modifier.weight(1f))
                    StatCard("Pending Tasks", assignments.count { !it.completed }.toString(), Icons.Default.Assignment, Modifier.weight(1f))
                }
                Spacer(Modifier.height(24.dp))
                SectionHeading("Active Courses", "View all")
            }
        }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 20.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(courses) { CourseInfo(it.name, it.code, it.instructor, it.progress) }
            }
        }
        item { Column(Modifier.padding(horizontal = 20.dp).padding(top = 24.dp)) { SectionHeading("Urgent Deadlines", "Upcoming") } }
        items(assignments.filter { !it.completed }.take(2)) { a ->
            Box(Modifier.padding(horizontal = 20.dp)) {
                AssignmentCard(a.title, a.course, a.dueDate, a.priority, a.completed) { onToggle(a.id) }
            }
        }
    }
}

@Composable
private fun StatCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = Blue)
            Spacer(Modifier.height(10.dp))
            Text(value, fontSize = 27.sp, color = Navy, fontWeight = FontWeight.Bold)
            Text(title, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
private fun SectionHeading(title: String, trailing: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(title, color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(trailing, color = Blue, fontSize = 12.sp)
    }
}
