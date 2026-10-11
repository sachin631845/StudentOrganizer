package com.example.studentorganizer.navigation

sealed class Screen(val route: String, val label: String) {
    data object Dashboard : Screen("dashboard", "Home")
    data object Assignments : Screen("assignments", "Tasks")
    data object Profile : Screen("profile", "Profile")
    data object Settings : Screen("settings", "Settings")
    data object AddAssignment : Screen("add_assignment", "Add Assignment")
}
