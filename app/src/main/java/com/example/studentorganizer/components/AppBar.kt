package com.example.studentorganizer.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF17324D)
private val PageBg = Color(0xFFF6F8FC)

@Composable
fun AppBar(title: String, canNavigateBack: Boolean = false, onBackClick: () -> Unit = {}) {
    Row(
        Modifier.fillMaxWidth().background(PageBg).padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (canNavigateBack) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Navy)
            }
            Spacer(Modifier.width(4.dp))
        }
        Text(title, color = Navy, fontSize = 22.sp, fontWeight = FontWeight.Bold)
    }
}
