package com.alphitardian.onboardingproject.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navigate: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    Surface(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        val profileState = viewModel.profile.observeAsState()
        val newsState = viewModel.news.observeAsState()
        val isUserLoggedin = viewModel.isLoggedin.value
        val alertDialog = remember { mutableStateOf(false) }

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
            }
            when (newsState.value) {
                is Resource.Success -> {
                    println(newsState.value)
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
            }
            if (profileState.value is Resource.Error || newsState.value is Resource.Error) {

            }
        }
        if (!isUserLoggedin) {
            alertDialog.value = true

            if (alertDialog.value) {
                NewsAlertDialog(errorMessage = "please login", state = alertDialog)
            }
        }
    }
}