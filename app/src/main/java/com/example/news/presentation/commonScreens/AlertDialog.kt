package com.example.news.presentation.commonScreens


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun alertDialog(
    onEnableClick: () -> Unit,
    onCancelClick: () -> Unit,
    tittle: String,
    description: String
) {
    AlertDialog(
        onDismissRequest = { onCancelClick() },
        title = {
            Text(text = tittle)
        },
        text = {
            Text(text = description)
        },
        confirmButton = {
            TextButton(onClick = { onEnableClick() }) {
                Text("ok")
            }
        },
        dismissButton = {
            TextButton(onClick = { onCancelClick() }) {
                Text("Cancel")
            }
        }
    )
}