package com.example.studentorganizer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.studentorganizer.components.AppBar
import com.example.studentorganizer.components.StudentProfile

private val Navy = Color(0xFF17324D)
private val Blue = Color(0xFF356AE6)

@Composable
fun ProfileScreen() {
    var showMessage by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        AppBar("Student Profile")
        StudentProfile("Sachin B R", "PES1UG24CS611", "Computer Science & Engineering")
        Spacer(Modifier.height(20.dp))
        Text("Academic Overview", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                AcademicRow("GPA", "3.82")
                HorizontalDivider()
                AcademicRow("Completed Credits", "84")
                HorizontalDivider()
                AcademicRow("Current Semester", "Fall 2026")
            }
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = { showMessage = true }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Blue)) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp)); Text("Edit Profile")
        }
        OutlinedButton(onClick = { showMessage = true }, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.FileDownload, contentDescription = null)
            Spacer(Modifier.width(8.dp)); Text("Export Academic Summary")
        }
        if (showMessage) Text("This is a UI demo. No profile changes or file export are performed.", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
private fun AcademicRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Color.Gray)
        Text(value, color = Navy, fontWeight = FontWeight.SemiBold)
    }
}
