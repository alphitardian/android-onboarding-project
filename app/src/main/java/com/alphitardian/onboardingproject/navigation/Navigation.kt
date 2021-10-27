package com.alphitardian.onboardingproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alphitardian.onboardingproject.presentation.home.HomeScreen
import com.alphitardian.onboardingproject.presentation.login.LoginScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navigate = {
                navController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            })
        }
        composable("home") {
            HomeScreen(navigate = {
                navController.navigate("login") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            })
        }
    }
}