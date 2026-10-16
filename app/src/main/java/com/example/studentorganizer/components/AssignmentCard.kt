package com.example.studentorganizer.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF17324D)
private val Green = Color(0xFF17865B)

@Composable
fun AssignmentCard(
    title: String, course: String, dueDate: String, priority: String,
    isCompleted: Boolean, onToggleComplete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCompleted, onCheckedChange = { onToggleComplete() }, colors = CheckboxDefaults.colors(checkedColor = Green))
            Column(Modifier.weight(1f)) {
                Text(title, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text(course, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                Spacer(Modifier.height(5.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(dueDate, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                val badgeColor = when (priority) { "High" -> Color(0xFFFFE8E6); "Medium" -> Color(0xFFFFF0D8); else -> Color(0xFFE1F5EB) }
                val textColor = when (priority) { "High" -> Color(0xFFB9382B); "Medium" -> Color(0xFF9A5B00); else -> Green }
                Surface(color = badgeColor, shape = RoundedCornerShape(8.dp)) {
                    Text(priority, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = textColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
                if (isCompleted) Text("Completed", color = Green, fontSize = 10.sp, modifier = Modifier.padding(top = 5.dp))
            }
        }
    }
}
