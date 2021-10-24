package com.alphitardian.onboardingproject.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.presentation.login.components.AuthenticationAlertDialog
import com.alphitardian.onboardingproject.presentation.login.components.TextInputField

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val loginState = viewModel.loginState.observeAsState()
    val loading = viewModel.loading.value
    val alertDialog = remember { mutableStateOf(false) }

    when (loginState.value) {
        is Resource.Success -> {
            viewModel.mutableLoginState.value = null
            navController.navigate("home") {
                popUpTo("login") {
                    inclusive = true
                }
            }
        }
        is Resource.Error -> {
            val errorMessage =
                (loginState.value as Resource.Error<TokenResponse>).error.localizedMessage
            val errorCode = errorMessage.split(" ").get(1)
            alertDialog.value = true

            when (ErrorState.fromRawValue(Integer.parseInt(errorCode))) {
                ErrorState.ERROR_400 -> AuthenticationAlertDialog(error = ErrorState.ERROR_400.code.toString(),
                    state = alertDialog)
                ErrorState.ERROR_401 -> AuthenticationAlertDialog(error = ErrorState.ERROR_401.code.toString(),
                    state = alertDialog)
                ErrorState.ERROR_422 -> AuthenticationAlertDialog(error = ErrorState.ERROR_422.code.toString(),
                    state = alertDialog)
                ErrorState.ERROR_UNKNOWN -> AuthenticationAlertDialog(error = ErrorState.ERROR_UNKNOWN.name,
                    state = alertDialog)
            }
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