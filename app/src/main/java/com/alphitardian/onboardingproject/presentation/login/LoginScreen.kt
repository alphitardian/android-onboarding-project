package com.alphitardian.onboardingproject.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.presentation.login.components.TextInputField

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = TextStyle(fontSize = 28.sp),
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(94.dp))
            TextInputField(
                title = stringResource(R.string.label_email),
                isPassword = false,
                value = email,
                onValueChange = { value ->
                    email = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            TextInputField(
                title = stringResource(R.string.label_password),
                isPassword = true,
                value = password,
                onValueChange = { value ->
                    password = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            Button(
                onClick = { /* TODO: Navigate to home screen */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.button_login),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}