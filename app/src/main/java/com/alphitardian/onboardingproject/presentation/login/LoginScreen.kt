package com.alphitardian.onboardingproject.presentation.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.presentation.login.components.TextInputField

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    val loginState = viewModel.loginState.observeAsState()
    val loading = viewModel.loading.value

    when (loginState.value) {
        is Resource.Success -> {
            Log.e("MAIN", "LoginScreen: sukses")
        }
        is Resource.Error -> {
            Log.e("MAIN", "LoginScreen: gagal")
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_title),
                style = TextStyle(fontSize = 28.sp),
                fontFamily = FontFamily.Serif,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(94.dp))
            TextInputField(
                title = stringResource(R.string.label_email),
                isPassword = false,
                value = viewModel.email.value,
                onValueChange = { value ->
                    viewModel.email.value = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            TextInputField(
                title = stringResource(R.string.label_password),
                isPassword = true,
                value = viewModel.password.value,
                onValueChange = { value ->
                    viewModel.password.value = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            Button(
                onClick = { viewModel.loginUser() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.button_login),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.height(40.dp)) {
                if (loading) {
                    CircularProgressIndicator(color = Color.Cyan)
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}