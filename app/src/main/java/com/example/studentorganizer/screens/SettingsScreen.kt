package com.example.studentorganizer.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.studentorganizer.components.AppBar

private val Navy = Color(0xFF17324D)

@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var startScreen by remember { mutableStateOf("Dashboard") }
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        AppBar("Settings")
        Text("Preferences", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(Modifier.height(10.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Dark Mode", color = Navy, fontWeight = FontWeight.SemiBold)
                        Text("Use a darker appearance", color = Color.Gray, fontSize = 12.sp)
                    }
                    Switch(checked = darkMode, onCheckedChange = { darkMode = it })
                }
                HorizontalDivider()
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Push Notifications", color = Navy, fontWeight = FontWeight.SemiBold)
                        Text("Assignment reminders", color = Color.Gray, fontSize = 12.sp)
                    }
                    Checkbox(checked = notifications, onCheckedChange = { notifications = it })
                }
            }
        }
        Spacer(Modifier.height(22.dp))
        Text("Default Start Screen", color = Navy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        listOf("Dashboard", "Assignments").forEach { option ->
            Row(Modifier.fillMaxWidth().clickable { startScreen = option }.padding(vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = startScreen == option, onClick = { startScreen = option })
                Text(option, color = Navy)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Preferences are kept in memory for this session only.", color = Color.Gray, fontSize = 12.sp)
    }
}
