package com.alphitardian.onboardingproject.presentation.login.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
                    color = MaterialTheme.colors.onBackground)
            },
            text = {
                Text(text = errorMessage,
                    color = MaterialTheme.colors.onBackground)
            },
            confirmButton = {
                OutlinedButton(onClick = { state.value = !state.value }) {
                    Text(text = stringResource(R.string.login_alert_confirm_button), color = MaterialTheme.colors.onBackground)
                }
            },
            modifier = Modifier.testTag(stringResource(id = R.string.testtag_login_auth_dialog))
        )
    }
}