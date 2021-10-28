package com.alphitardian.onboardingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alphitardian.onboardingproject.presentation.home.HomeScreen
import com.alphitardian.onboardingproject.presentation.login.LoginScreen
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnboardingProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen()
                }
            }
        }
    }
}
