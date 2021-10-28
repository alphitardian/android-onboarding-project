package com.alphitardian.onboardingproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_HOME
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_LOGIN
import com.alphitardian.onboardingproject.presentation.home.HomeScreen
import com.alphitardian.onboardingproject.presentation.login.LoginScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(DESTINATION_LOGIN) {
            LoginScreen(navigate = {
                navController.navigate(DESTINATION_HOME) {
                    popUpTo(DESTINATION_LOGIN) {
                        inclusive = true
                    }
                }
            })
        }
        composable(DESTINATION_HOME) {
            HomeScreen(navigate = {
                navController.navigate(DESTINATION_LOGIN) {
                    popUpTo(DESTINATION_HOME) {
                        inclusive = true
                    }
                }
            })
        }
    }
}