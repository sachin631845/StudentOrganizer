package com.example.studentorganizer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF17324D)

@Composable
fun StudentProfile(studentName: String, studentId: String, major: String, avatarRes: Int = 0) {
    Card(colors = CardDefaults.cardColors(containerColor = Navy), shape = RoundedCornerShape(20.dp)) {
        Row(Modifier.fillMaxWidth().padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(68.dp).background(Color.White.copy(alpha = .15f), CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Default.Person, contentDescription = "Student avatar", tint = Color.White, modifier = Modifier.size(42.dp))
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(studentName, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(studentId, color = Color.White.copy(alpha = .8f), fontSize = 13.sp)
                Text(major, color = Color(0xFFBFD1FF), fontSize = 13.sp)
            }
        }
    }
}
