package com.example.studentorganizer.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studentorganizer.components.BottomBar
import com.example.studentorganizer.models.Assignment
import com.example.studentorganizer.models.Course
import com.example.studentorganizer.screens.*

private val Blue = Color(0xFF356AE6)
private val PageBg = Color(0xFFF6F8FC)

@Composable
fun StudentOrganizerApp() {
    val navController = rememberNavController()
    val courses = remember {
        listOf(
            Course("Data Structures", "UE24CS201", "Dr. Rao", .72f),
            Course("Database Systems", "UE24CS251", "Prof. Mehta", .58f),
            Course("Machine Learning", "UE24CS352", "Dr. Nair", .83f),
            Course("Computer Networks", "UE24CS301", "Prof. Kumar", .41f)
        )
    }
    var assignments by remember {
        mutableStateOf(
            listOf(
                Assignment(1, "DBMS Lab Record", "Database Systems", "Oct 13, 2026", "High"),
                Assignment(2, "Graph Algorithms", "Data Structures", "Oct 15, 2026", "Medium"),
                Assignment(3, "ML Mini Project", "Machine Learning", "Oct 17, 2026", "High"),
                Assignment(4, "Network Notes", "Computer Networks", "Oct 20, 2026", "Low", completed = true)
            )
        )
    }
    val backStack by navController.currentBackStackEntryAsState()
    val currentRoute = backStack?.destination?.route
    val showBottomBar = currentRoute != Screen.AddAssignment.route

    Scaffold(
        containerColor = PageBg,
        bottomBar = { if (showBottomBar) BottomBar(navController, currentRoute) },
        floatingActionButton = {
            if (currentRoute == Screen.Dashboard.route || currentRoute == Screen.Assignments.route) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddAssignment.route) },
                    containerColor = Blue,
                    contentColor = Color.White
                ) { Icon(Icons.Default.Add, contentDescription = "Add assignment") }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(courses, assignments) { id ->
                    assignments = assignments.map { if (it.id == id) it.copy(completed = !it.completed) else it }
                }
            }
            composable(Screen.Assignments.route) {
                AssignmentsScreen(assignments) { id ->
                    assignments = assignments.map { if (it.id == id) it.copy(completed = !it.completed) else it }
                }
            }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
            composable(Screen.AddAssignment.route) {
                AddAssignmentScreen(
                    courses = courses,
                    onCancel = { navController.popBackStack() },
                    onSave = { title, course, date, priority, description, reminder ->
                        val nextId = (assignments.maxOfOrNull { it.id } ?: 0) + 1
                        assignments = assignments + Assignment(
                            nextId, title, course, date, priority, description, reminder
                        )
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
