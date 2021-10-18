package com.alphitardian.onboardingproject.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alphitardian.onboardingproject.presentation.home.components.TopBar
import com.alphitardian.onboardingproject.presentation.home.components.NewsContent

@Composable
fun HomeScreen() {
    Surface(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
        Column {
            TopBar()
            NewsContent()
        }
    }
}