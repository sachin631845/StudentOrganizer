package com.example.studentorganizer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)
private val PaleBlue = Color(0xFFEAF0FF)

@Composable
fun CourseInfo(
    courseName: String,
    courseCode: String,
    instructor: String,
    progress: Float,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.width(230.dp).clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Box(Modifier.size(38.dp).background(PaleBlue, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.MenuBook, contentDescription = null, tint = Blue)
            }
            Spacer(Modifier.height(12.dp))
            Text(courseName, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
            Text(courseCode, color = Blue, fontSize = 12.sp)
            Spacer(Modifier.height(5.dp))
            Text("Instructor: $instructor", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Progress", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                Text("${(progress.coerceIn(0f, 1f) * 100).toInt()}%", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth().height(6.dp),
                color = Blue, trackColor = PaleBlue
            )
            Spacer(Modifier.height(6.dp))
            Text("Tap to view assignments", color = Blue, fontSize = 10.sp)
        }
    }
}
