package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.alphitardian.onboardingproject.R

@Composable
fun NewsAlertDialog(errorMessage: String, confirmOnClick: () -> Unit, dismissOnClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { null },
        title = {
            Text(text = "Your session is expired",
                style = TextStyle(color = MaterialTheme.colors.onBackground))
        },
        text = {
            Text(text = errorMessage,
                style = TextStyle(color = MaterialTheme.colors.onBackground))
        },
        confirmButton = {
            OutlinedButton(onClick = { confirmOnClick() },
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.primary)) {
                Text(text = stringResource(R.string.login_alert_confirm_button),
                    style = TextStyle(color = MaterialTheme.colors.onPrimary))
            }
        },
        dismissButton = {
            TextButton(onClick = { dismissOnClick()}) {
                Text(text = "Log Out", style = TextStyle(color = Color.Red))
            }
        }
    )
}