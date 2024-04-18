package com.example.news.presentation.navScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@Composable
fun ScienceScreen() {
    Box(modifier = Modifier.fillMaxWidth()
        .fillMaxHeight()) {
        Text(
            text = "Screen under development",
            style = TextStyle(color = Color.Red, fontSize = 24.sp),
            modifier = Modifier.align(Alignment.Center))
    }
}