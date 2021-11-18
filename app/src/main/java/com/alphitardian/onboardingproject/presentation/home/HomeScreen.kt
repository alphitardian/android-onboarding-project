package com.alphitardian.onboardingproject.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.ErrorState
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.presentation.home.components.NewsAlertDialog
import com.alphitardian.onboardingproject.presentation.home.components.NewsContent
import com.alphitardian.onboardingproject.presentation.home.components.TopBar
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navigate: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        val profileState = viewModel.profile.observeAsState()
        val newsState = viewModel.news.observeAsState()
        val refreshTokenState = viewModel.refreshToken.observeAsState()
        val isUserLoggedin = viewModel.isLoggedin.value

        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val alertDialog = remember { mutableStateOf(false) }

        Scaffold(scaffoldState = scaffoldState, snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, backgroundColor = Color.DarkGray)
            }
        }, modifier = Modifier.testTag(stringResource(R.string.testtag_home_content))
        ) {
            Column {
                when (val profileResource = profileState.value) {
                    is Resource.Success -> {
                        TopBar(userProfile = profileResource.data)
                    }
                    is Resource.Loading -> {
                        LoadingStateIndicator()
                    }
                    is Resource.Error -> {
                        TopBar(userProfile = null)
                    }
                }
                when (val newsResource = newsState.value) {
                    is Resource.Success -> {
                        NewsContent(news = newsResource.data)
                    }
                    is Resource.Loading -> {
                        NewsContent(news = null)
                    }
                    is Resource.Error -> {
                        Box(modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center) {
                            when (newsResource.code) {
                                ErrorState.ERROR_401.code -> Text(text = stringResource(R.string.home_message_auth_failed))
                                else -> Text(text = stringResource(R.string.home_message_connection_failed))
                            }
                        }
                    }
                }
                when (refreshTokenState.value) {
                    is Resource.Success -> {
                        val successMessage =
                            stringResource(id = R.string.home_snackbar_success_description)
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = successMessage)
                        }
                    }
                    is Resource.Loading -> Unit
                    is Resource.Error -> {
                        val errorMessage = stringResource(id = R.string.home_snackbar_error_description)
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = errorMessage)
                        }
                    }
                }
            }
        }

        if (!isUserLoggedin) {
            alertDialog.value = true

            if (alertDialog.value) {
                NewsAlertDialog(
                    errorMessage = stringResource(R.string.home_alert_description),
                    confirmOnClick = {
                        navigate()
                    }
                )
            }
        }
    }
}

@Composable
fun LoadingStateIndicator() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Transparent),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}