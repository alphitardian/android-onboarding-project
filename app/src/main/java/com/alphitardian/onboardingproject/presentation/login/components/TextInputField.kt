package com.alphitardian.onboardingproject.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.ErrorResponse

@Composable
fun TextInputField(
    title: String,
    isPassword: Boolean,
    value: String,
    isError: ErrorResponse? = null,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = MaterialTheme.colors.onSurface)
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(
                    text = String.format(
                        stringResource(id = R.string.hint_login_input),
                        title.lowercase()
                    )
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface,
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.onBackground
            ),
            visualTransformation = if (!passwordVisibility && isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
            ),
            trailingIcon = {
                if (isPassword) {
                    val icon =
                        if (passwordVisibility) R.drawable.ic_baseline_visibility_24 else R.drawable.ic_baseline_visibility_off_24

                    IconButton(onClick = { passwordVisibility = !passwordVisibility },
                        modifier = Modifier.testTag(
                            stringResource(R.string.testtag_login_password_visibility_icon))) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = stringResource(R.string.content_description_visibility_icon)
                        )
                    }
                }
            },
            modifier = modifier.fillMaxWidth(),
            isError = isError != null,
        )
        if (isError != null) {
            Text(text = isError.fields[0].error,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.testTag(stringResource(R.string.testtag_login_error_message))
            )
        }
    }
}