package com.alphitardian.onboardingproject.presentation.home.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.alphitardian.onboardingproject.R

@Composable
fun NewsAlertDialog(errorMessage: String, confirmOnClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { null },
        title = {
            Text(text = stringResource(R.string.home_alert_title),
                color = MaterialTheme.colors.onBackground)
        },
        text = {
            Text(text = errorMessage,
                color = MaterialTheme.colors.onBackground)
        },
        confirmButton = {
            OutlinedButton(onClick = { confirmOnClick() }) {
                Text(text = stringResource(R.string.login_alert_confirm_button),
                    color = MaterialTheme.colors.onBackground)
            }
        }
    )
}