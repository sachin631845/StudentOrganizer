package com.example.studentorganizer.navigation

sealed class Screen(val route: String, val label: String) {
    data object Dashboard : Screen("dashboard", "Home")
    data object Assignments : Screen("assignments", "Tasks")
    data object Profile : Screen("profile", "Profile")
    data object Settings : Screen("settings", "Settings")
    data object AddAssignment : Screen("add_assignment", "Add Assignment")
}
//A sealed class restricts which classes or objects can directly inherit from it.
// Here, it keeps the set of screen destinations organised in one place.

//defines the singleton object
//dashboard as the destignation screen with the label value
//A sealed class restricts the possible subclasses to a known set,
// making code easier to organise and reason about.
