package com.alphitardian.onboardingproject.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alphitardian.onboardingproject.presentation.home.HomeScreen
import com.alphitardian.onboardingproject.presentation.login.LoginScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Destination.DESTINATION_LOGIN.name) {
            LoginScreen(navigate = {
                navController.navigate(Destination.DESTINATION_HOME.name) {
                    popUpTo(Destination.DESTINATION_LOGIN.name) {
                        inclusive = true
                    }
                }
            })
        }
        composable(Destination.DESTINATION_HOME.name) {
            HomeScreen(navigate = {
                navController.navigate(Destination.DESTINATION_LOGIN.name) {
                    popUpTo(Destination.DESTINATION_HOME.name) {
                        inclusive = true
                    }
                }
            })
        }
    }
}