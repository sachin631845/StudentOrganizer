package com.example.studentorganizer.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)
private val PaleBlue = Color(0xFFEAF0FF)

@Composable
fun CourseInfo(courseName: String, courseCode: String, instructor: String, progress: Float) {
    Card(
        modifier = Modifier.width(230.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Box(Modifier.size(38.dp).background(PaleBlue, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.MenuBook, contentDescription = null, tint = Blue)
            }
            Spacer(Modifier.height(12.dp))
            Text(courseName, fontWeight = FontWeight.Bold, color = Navy, fontSize = 16.sp)
            Text(courseCode, color = Blue, fontSize = 12.sp)
            Spacer(Modifier.height(5.dp))
            Text("Instructor: $instructor", color = Color.Gray, fontSize = 12.sp)
            Spacer(Modifier.height(14.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Progress", color = Color.Gray, fontSize = 12.sp)
                Text("${(progress * 100).toInt()}%", color = Navy, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress }, modifier = Modifier.fillMaxWidth().height(6.dp),
                color = Blue, trackColor = PaleBlue
            )
        }
    }
}
