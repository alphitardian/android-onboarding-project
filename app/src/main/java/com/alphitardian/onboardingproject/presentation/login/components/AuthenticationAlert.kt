package com.alphitardian.onboardingproject.presentation.login.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.alphitardian.onboardingproject.R

@Composable
fun AuthenticationAlertDialog(errorMessage: String, state: MutableState<Boolean>) {
    if (state.value) {
        AlertDialog(
            onDismissRequest = { state.value = !state.value },
            title = {
                Text(text = stringResource(R.string.login_alert_title),
                    style = TextStyle(color = MaterialTheme.colors.onBackground))
            },
            text = {
                Text(text = errorMessage,
                    style = TextStyle(color = MaterialTheme.colors.onBackground))
            },
            confirmButton = {
                OutlinedButton(onClick = { state.value = !state.value },
                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.primary)) {
                    Text(text = stringResource(R.string.login_alert_confirm_button),
                        style = TextStyle(color = MaterialTheme.colors.onPrimary))
                }
            }
        )
    }
}