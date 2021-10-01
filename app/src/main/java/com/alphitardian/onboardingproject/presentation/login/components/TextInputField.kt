package com.alphitardian.onboardingproject.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.alphitardian.onboardingproject.R

@Composable
fun TextInputField(
    title: String,
    isPassword: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title, color = Color.Gray)
        TextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = {
                Text(text = "Type your ${title.lowercase()} here")
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
            ),
            trailingIcon = {
                if (isPassword) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_visibility_off_24),
                        contentDescription = "Show password Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}