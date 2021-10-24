package com.alphitardian.onboardingproject.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.alphitardian.onboardingproject.presentation.home.components.NewsContent
import com.alphitardian.onboardingproject.presentation.home.components.TopBar

@Composable
fun HomeScreen(navController: NavHostController) {
    Surface(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        Column {
            TopBar()
            NewsContent()
        }
    }
}