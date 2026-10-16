
package com.example.studentorganizer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentorganizer.components.AppBar
import com.example.studentorganizer.components.StudentProfile

@Composable
fun ProfileScreen(
    name: String,
    studentId: String,
    program: String,
    onSave: (String, String, String) -> Unit
) {
    var showEdit by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(name) }
    var editId by remember { mutableStateOf(studentId) }
    var editProgram by remember { mutableStateOf(program) }
    var errorMessage by remember { mutableStateOf("") }
    var showCopied by remember { mutableStateOf(false) }

    val clipboard = LocalClipboardManager.current

    // Keep the edit fields synchronized with the saved profile.
    LaunchedEffect(name, studentId, program) {
        editName = name
        editId = studentId
        editProgram = program
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AppBar("Student Profile")

        StudentProfile(
            studentName = name.ifBlank { "Your name" },
            studentId = studentId.ifBlank { "Add student ID" },
            major = program.ifBlank { "Add program" }
        )

        Text(
            text = "Academic Statistics",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AcademicRow("GPA", "3.82")
                HorizontalDivider()
                AcademicRow("Completed Credits", "84")
                HorizontalDivider()
                AcademicRow("Current Semester", "Fall 2026")
            }
        }

        Text(
            text = "Student Information",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AcademicRow("Name", name.ifBlank { "Not set" })
                AcademicRow("Student ID", studentId.ifBlank { "Not set" })
                AcademicRow("Program", program.ifBlank { "Not set" })
            }
        }

        Button(
            onClick = {
                editName = name
                editId = studentId
                editProgram = program
                errorMessage = ""
                showEdit = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Edit Profile")
        }

        OutlinedButton(
            onClick = {
                val summary = """
                    Academic Summary
                    Name: ${name.ifBlank { "Not set" }}
                    Student ID: ${studentId.ifBlank { "Not set" }}
                    Program: ${program.ifBlank { "Not set" }}
                    GPA: 3.82
                    Completed Credits: 84
                    Current Semester: Fall 2026
                """.trimIndent()

                clipboard.setText(AnnotatedString(summary))
                showCopied = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.FileDownload, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Copy Academic Summary")
        }

        if (showCopied) {
            Text(
                text = "Academic summary copied to clipboard.",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 12.sp
            )
        }
    }

    if (showEdit) {
        AlertDialog(
            onDismissRequest = {
                showEdit = false
                errorMessage = ""
            },
            title = {
                Text("Edit Student Profile")
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = {
                            editName = it
                            errorMessage = ""
                        },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editId,
                        onValueChange = {
                            editId = it
                            errorMessage = ""
                        },
                        label = { Text("Student ID / SRN") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editProgram,
                        onValueChange = {
                            editProgram = it
                            errorMessage = ""
                        },
                        label = { Text("Program / Department") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (errorMessage.isNotBlank()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 13.sp
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editName.isBlank() ||
                            editId.isBlank() ||
                            editProgram.isBlank()
                        ) {
                            errorMessage = "Please fill in all three fields."
                        } else {
                            onSave(
                                editName.trim(),
                                editId.trim(),
                                editProgram.trim()
                            )
                            showEdit = false
                            errorMessage = ""
                        }
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEdit = false
                        errorMessage = ""
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun AcademicRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold
        )
    }
}
