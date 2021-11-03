package com.alphitardian.onboardingproject.presentation.login

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.ErrorResponse
import com.alphitardian.onboardingproject.data.auth.data_source.remote.response.TokenResponse
import com.alphitardian.onboardingproject.presentation.login.components.AuthenticationAlertDialog
import com.alphitardian.onboardingproject.presentation.login.components.TextInputField

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(navigate: () -> Unit, viewModel: LoginViewModel = hiltViewModel()) {
    val loginState = viewModel.loginState.observeAsState()
    val alertDialog = remember { mutableStateOf(false) }
    val fieldValidation = remember { mutableStateOf<ErrorResponse?>(null) }

    when (loginState.value) {
        is Resource.Success -> {
            viewModel.mutableLoginState.value = null
            navigate()
        }
        is Resource.Error -> {
            alertDialog.value = true
            when ((loginState.value as Resource.Error<TokenResponse>).code) {
                ErrorState.ERROR_400.code -> AuthenticationAlertDialog(errorMessage = String.format(
                    stringResource(id = R.string.login_alert_description),
                    ErrorState.ERROR_400.code.toString()),
                    state = alertDialog)
                ErrorState.ERROR_401.code -> AuthenticationAlertDialog(errorMessage = String.format(
                    stringResource(id = R.string.login_alert_description),
                    ErrorState.ERROR_401.code.toString()),
                    state = alertDialog)
                ErrorState.ERROR_422.code -> {

                    fieldValidation.value =
                        viewModel.handleFieldValidation((loginState.value as Resource.Error<TokenResponse>).error)
                }
                ErrorState.ERROR_UNKNOWN.code -> AuthenticationAlertDialog(errorMessage = stringResource(
                    id = R.string.login_alert_connection_description),
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
                modifier = Modifier.testTag(stringResource(id = R.string.testtag_login_email)),
                isError = if (fieldValidation.value?.fields?.get(0)?.name == stringResource(id = R.string.label_username).toLowerCase()) fieldValidation.value else null,
                onValueChange = { value ->
                    viewModel.email.value = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            TextInputField(
                title = stringResource(R.string.label_password),
                isPassword = true,
                value = viewModel.password.value,
                modifier = Modifier.testTag(stringResource(id = R.string.testtag_login_password)),
                isError = if (fieldValidation.value?.fields?.get(0)?.name == stringResource(id = R.string.label_password).toLowerCase()) fieldValidation.value else null,
                onValueChange = { value ->
                    viewModel.password.value = value
                })
            Spacer(modifier = Modifier.height(53.dp))
            Button(
                onClick = {
                    viewModel.loginUser()
                    fieldValidation.value = null
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(stringResource(id = R.string.testtag_login_button))
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
                if (loginState.value is Resource.Loading) {
                    CircularProgressIndicator(color = Color.Cyan)
                }
            }
        }
    }
}