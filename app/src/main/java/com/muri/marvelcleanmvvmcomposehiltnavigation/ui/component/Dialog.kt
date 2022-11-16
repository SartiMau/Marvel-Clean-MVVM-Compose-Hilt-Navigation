package com.muri.marvelcleanmvvmcomposehiltnavigation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.muri.marvelcleanmvvmcomposehiltnavigation.ui.theme.MarvelCleanMVVMComposeHiltNavigationTheme

@Composable
fun ErrorDialog(exception: Exception, onError: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onError()
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onError()
                    }
                ) {
                    Text(text = "Dismiss")
                }
            },
            icon = { Icon(Icons.Filled.Warning, contentDescription = "Warning") },
            title = {
                Text(text = "Something got wrong!")
            },
            text = {
                Text(text = exception.message ?: "Se shrompió")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ErrorDialogPreview() {
    MarvelCleanMVVMComposeHiltNavigationTheme {
        ErrorDialog(Exception("Se shrompio!")) { }
    }
}
