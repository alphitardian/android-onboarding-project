package com.alphitardian.onboardingproject.presentation.login.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextStyle

@Composable
fun AuthenticationAlertDialog(error: String, state: MutableState<Boolean>) {
    if (state.value) {
        AlertDialog(
            onDismissRequest = { state.value = false },
            title = {
                Text(text = "Login Failed",
                    style = TextStyle(color = MaterialTheme.colors.onBackground))
            },
            text = {
                Text(text = "An error $error occured, please check your username or password",
                    style = TextStyle(color = MaterialTheme.colors.onBackground))
            },
            confirmButton = {
                OutlinedButton(onClick = { state.value = false },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.primary)) {
                    Text(text = "OK", style = TextStyle(color = MaterialTheme.colors.onPrimary))
                }
            }
        )
    }
}