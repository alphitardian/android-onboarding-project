package com.alphitardian.onboardingproject.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alphitardian.onboardingproject.common.Resource
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.news.NewsItemResponse
import com.alphitardian.onboardingproject.data.user.data_source.remote.response.user.UserResponse
import com.alphitardian.onboardingproject.presentation.home.components.NewsAlertDialog
import com.alphitardian.onboardingproject.presentation.home.components.NewsContent
import com.alphitardian.onboardingproject.presentation.home.components.TopBar
import kotlinx.coroutines.launch
import retrofit2.HttpException

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navigate: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        val profileState = viewModel.profile.observeAsState()
        val newsState = viewModel.news.observeAsState()
        val refreshTokenState = viewModel.refreshToken.observeAsState()

        val isUserLoggedin = viewModel.isLoggedin.value
        val currentToken = viewModel.userDecryptedToken.value

        val scaffoldState = rememberScaffoldState()
        val coroutineScope = rememberCoroutineScope()
        val alertDialog = remember { mutableStateOf(false) }

        Scaffold(scaffoldState = scaffoldState, snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, backgroundColor = Color.DarkGray)
            }
        }) {
            Column {
                when (profileState.value) {
                    is Resource.Success -> {
                        TopBar(userProfile = (profileState.value as Resource.Success<UserResponse>).data)
                    }
                    is Resource.Loading -> {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .background(color = Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.background(color = Color.Transparent))
                        }
                    }
                    is Resource.Error -> {
                        if ((profileState.value as Resource.Error<UserResponse>).error is HttpException) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), contentAlignment = Alignment.Center) {
                                Text(text = "No authorized")
                            }
                        } else {
                            TopBar(userProfile = null)
                        }
                    }
                }
                when (newsState.value) {
                    is Resource.Success -> {
                        NewsContent(
                            news = (newsState.value as Resource.Success<List<NewsItemResponse>>).data,
                            viewModel = viewModel
                        )
                    }
                    is Resource.Loading -> {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.background(color = Color.Transparent))
                        }
                    }
                    is Resource.Error -> {
                        if ((newsState.value as Resource.Error<List<NewsItemResponse>>).error is HttpException) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), contentAlignment = Alignment.Center) {
                                Text(text = "No authorized")
                            }
                        } else {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp), contentAlignment = Alignment.Center) {
                                Text(text = "No Connection")
                            }
                        }
                    }
                }
            }
            when (refreshTokenState.value) {
                is Resource.Success -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Token refreshed!")
                    }
                }
                is Resource.Loading -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Error -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("An error occured, please try to log in.")
                    }
                }
            }
        }

        if (!isUserLoggedin) {
            alertDialog.value = true

            if (alertDialog.value) {
                NewsAlertDialog(
                    errorMessage = "Press OK to refresh, press LOGOUT to go to Login Screen.",
                    confirmOnClick = {
                        currentToken?.decodeToString()?.let { viewModel.getNewToken(it) }
                        alertDialog.value = false
                    },
                    dismissOnClick = {
                        navigate()
                    }
                )
            }
        }
    }
}