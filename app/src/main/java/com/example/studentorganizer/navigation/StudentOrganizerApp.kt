
package com.example.studentorganizer.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.studentorganizer.components.BottomBar
import com.example.studentorganizer.models.Assignment
import com.example.studentorganizer.models.Course
import com.example.studentorganizer.screens.*

private val Blue = Color(0xFF356AE6)
private val LightBackground = Color(0xFFF6F8FC)
private val LightSurface = Color(0xFFFFFFFF)
private val DarkBackground = Color(0xFF121821)
private val DarkSurface = Color(0xFF202936)
//These variables store colour values used throughout
// the application to maintain a consistent appearance
// in light and dark modes.

@Composable
fun StudentOrganizerApp() {
    val navController = rememberNavController()
    //creates and remembers a navigation controller that manages
    // navigation between screens.

    val courses = remember {
        listOf(
            Course("Advance Algorithm", "CS201", "Course instructor", 0.72f),
            Course("Cyber Security", "CS251", "Course instructor", 0.58f),
            Course("Machine Learning", "CS352", "Course instructor", 0.83f),
            Course("Cloud Computing", "CS301", "Course instructor", 0.41f),
            Course("Object Oriented programming and design with java", "CS308", "Course instructor", 0.87f)

        )
    }
    //- { } contains the code that creates the list.
    //- listOf() creates a read-only list of elements.
    //Here, the course list is created once and remembered
    // instead of being recreated during every recomposition.

    var assignments by remember {
        //reassigned when an assignment is added or its c
        // ompletion status changes.
        mutableStateOf(
            //mutableStateOf() creates observable state for
            // Jetpack Compose, initially containing a list of
            // assignments. When the state is updated,
            // Compose can recompose the UI to reflect the changes.
            listOf(
                Assignment(
                    1, "lab record", "Advance Algorithm",
                    "Oct 13, 2026", "High"
                ),
                Assignment(
                    2, "Write notes", "Cloud Computing",
                    "Oct 15, 2026", "Medium"
                ),
                Assignment(
                    3, "ML mini project", "Machine Learning",
                    "Oct 17, 2026", "High"
                ),
                Assignment(
                    4, "Record", "Cyber Security",
                    "Oct 20, 2026", "Low", completed = true
                )
            )
        )
    }

    // Student profile state
    var studentName by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var studentProgram by remember { mutableStateOf("") }
    //mutableStateOf("") creates observable state with an initially
    // empty string. remember retains the state during recomposition,
    // and by allows us to access and update the value directly.
    //When the user edits and saves their profile, these variables
    // can be updated, and Compose can refresh the UI.

    // Settings state
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var startScreen by remember { mutableStateOf("Dashboard") }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
//The first line observes which screen the user is on, and the
// second line gets that screen's route name so the app can decide
// what to display.
    val isDark = darkMode

    val appColorScheme = if (isDark) {
        darkColorScheme(
            primary = Color(0xFF8EABFF),
            onPrimary = Color(0xFF10265C),
            secondary = Color(0xFFB7C5FF),
            background = DarkBackground,
            onBackground = Color(0xFFE8EDF5),
            surface = DarkSurface,
            onSurface = Color(0xFFE8EDF5),
            surfaceVariant = Color(0xFF303B4B),
            onSurfaceVariant = Color(0xFFC0CAD8),
            outline = Color(0xFF596579)
        )
    } else {
        lightColorScheme(
            primary = Blue,
            onPrimary = Color.White,
            secondary = Color(0xFF526AA5),
            background = LightBackground,
            onBackground = Color(0xFF172033),
            surface = LightSurface,
            onSurface = Color(0xFF172033),
            surfaceVariant = Color(0xFFE9EDF5),
            onSurfaceVariant = Color(0xFF566176),
            outline = Color(0xFFD5DBE5)
        )
    }

    //This applies the selected colour scheme to all UI components inside the block.
    // appColorScheme contains the light or dark colour scheme selected earlier in the code.

    MaterialTheme(colorScheme = appColorScheme) {
        val backgroundColor = MaterialTheme.colorScheme.background
        val showBottomBar = currentRoute != Screen.AddAssignment.route

        Scaffold(
            //Scaffold is a Jetpack Compose Material component that provides a standard screen layout
            // with areas for elements such as a bottom navigation bar, a floating action button and the
            // main content.
            modifier = Modifier,
            containerColor = backgroundColor,
            bottomBar = {
                if (showBottomBar) {
                    BottomBar(navController, currentRoute)
                }
            },
            floatingActionButton = {
                if (
                    currentRoute == Screen.Dashboard.route ||
                    currentRoute == Screen.Assignments.route
                ) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(Screen.AddAssignment.route)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add assignment"
                        )
                    }
                }
            }
        ) { innerPadding ->
            //innerPadding represents the space needed to avoid
            // overlapping Scaffold elements, such as the bottom
            // navigation bar and other layout areas.
            NavHost(
                //NavHost defines which composable screen
                // should be displayed for each navigation destination.
                navController = navController,
                startDestination = if (startScreen == "Assignments") {
                    Screen.Assignments.route
                } else {
                    Screen.Dashboard.route
                },
                modifier = Modifier.padding(innerPadding)
                //adds padding so the screen content doesn't overlap the Scaffold's reserved layout areas.
            ) {

                //dashboard as the destination scrren in the navigation graph
                composable(Screen.Dashboard.route) {
                    DashboardScreen(
                        studentName = studentName,
                        courses = courses,
                        assignments = assignments,
                        //recieves the course id of the assignments the user wants to complete or incomplete
                        onToggle = { id ->
                            assignments = assignments.map { assignment ->
                                if (assignment.id == id) {
                                    //Checks whether the current assignment's ID matches the ID received by the
                                    // callback. If they match, that assignment is updated; otherwise, it remains unchanged.
                                    assignment.copy(
                                        completed = !assignment.completed
                                        //new instance of the assignment with an updated completion status
                                    )
                                } else {
                                    assignment
                                    //Returns the original assignment unchanged if its ID does not match the selected ID.
                                }
                            }
                        },
                        onViewAssignments = {
                            navController.navigate(Screen.Assignments.route) {
                                launchSingleTop = true
                                //prevents a duplicate copy of the Assignments screen from being added to the top
                            // of the navigation back stack when Assignments is already the current top screen.
                            }
                        }
                    )
                }

                composable(Screen.Assignments.route) {
                    //function and passes the current list of assignments so the screen can display them.
                    AssignmentsScreen(assignments) { id ->
                        assignments = assignments.map { assignment ->
                            if (assignment.id == id) {
                                assignment.copy(
                                    completed = !assignment.completed
                                )
                            } else {
                                assignment
                            }
                        }
                    }
                    //Uses map() to iterate through all assignments and create a new list. The updated
                // list replaces the existing assignments state.
                }

                composable(Screen.Profile.route) {
                    ProfileScreen(
                        name = studentName,
                        studentId = studentId,
                        program = studentProgram,
                        onSave = { name, id, program ->
                            studentName = name
                            studentId = id
                            studentProgram = program
                        }
                    )
                    //Defines a callback that receives three values when the user saves the profile:
                // the name, student ID and programme.
                }

                composable(Screen.Settings.route) {
                    SettingsScreen(
                        darkMode = darkMode,
                        notifications = notifications,
                        startScreen = startScreen,
                        onDarkModeChange = { enabled ->
                            darkMode = enabled
                        },
                        onNotificationsChange = { enabled ->
                            notifications = enabled
                            //Receives the new notification preference and updates the notifications state variable.
                        },
                        onStartScreenChange = { chosen ->
                            startScreen = chosen
                            //efines a callback that executes when the user selects a starting screen.
                            // The chosen parameter contains the selected screen name.

                            val destination =
                                if (chosen == "Assignments") {
                                    Screen.Assignments.route
                                } else {
                                    Screen.Dashboard.route
                                }

                            navController.navigate(destination) {
                                launchSingleTop = true
                                popUpTo(Screen.Dashboard.route) {
                                    inclusive = false
                                }
                            }
                        }
                        //dded to the top of the navigation stack if that destination is already at the top.
                        //Removes destinations above Dashboard from the navigation stack while keeping Dashboard itsel
                    )
                }

                composable(Screen.AddAssignment.route) {
                    AddAssignmentScreen(
                        courses = courses,
                        //Calls the Add Assignment screen and passes the list of available courses so the user can
                        // select a course for the new assignment.
                        onCancel = {
                            navController.popBackStack()
                            //previous screen by removing the current destination from the navigation stack.
                        },
                        onSave = {
                                title,
                                course,
                                date,
                                priority,
                                description,
                                reminder ->
                            //callback that receives the assignment details entered by the user:

                            val nextId =
                                (assignments.maxOfOrNull { it.id } ?: 0) + 1
                            //argest existing assignment ID and adds one to generate the next ID.

                            val newAssignment = Assignment(
                                nextId,
                                title,
                                course,
                                date,
                                priority,
                                description,
                                reminder
                            )

                            assignments = assignments + newAssignment
                            navController.popBackStack()
                        }
                    )
                    //Add Assignment destination from the navigation stack and returns the user
                // to the previous screen.
                }
            }
        }
    }
}
