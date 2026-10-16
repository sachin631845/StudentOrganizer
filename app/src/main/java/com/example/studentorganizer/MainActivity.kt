
package com.example.studentorganizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.studentorganizer.navigation.StudentOrganizerApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StudentOrganizerApp()
        }
    }
}

//`MainActivity.kt` is the entry point of the Student Organizer
// Android application. It extends `ComponentActivity` to access
// Android activity functionality. The `onCreate()` method runs
// when the activity is created. `super.onCreate(savedInstanceState)`
// calls the parent class implementation. `setContent { }` sets up th
// e user interface using Jetpack Compose, and `StudentOrganizerApp()`
// launches the main application UI and navigation.




