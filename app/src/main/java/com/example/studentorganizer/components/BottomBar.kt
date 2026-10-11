package com.example.studentorganizer.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studentorganizer.navigation.Screen

private val Blue = Color(0xFF356AE6)
private val PaleBlue = Color(0xFFEAF0FF)

@Composable
fun BottomBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar(containerColor = Color.White) {
        val tabs = listOf(
            Triple(Screen.Dashboard, Icons.Default.Home, "Dashboard"),
            Triple(Screen.Assignments, Icons.Default.CheckCircle, "Assignments"),
            Triple(Screen.Profile, Icons.Default.Person, "Profile"),
            Triple(Screen.Settings, Icons.Default.Settings, "Settings")
        )
        tabs.forEach { (screen, icon, label) ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Dashboard.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blue,
                    indicatorColor = PaleBlue
                )
            )
        }
    }
}
