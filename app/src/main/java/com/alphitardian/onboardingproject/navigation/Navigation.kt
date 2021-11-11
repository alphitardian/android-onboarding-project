package com.alphitardian.onboardingproject.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import com.alphitardian.onboardingproject.presentation.home.HomeScreen
import com.alphitardian.onboardingproject.presentation.login.LoginScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Destination.DESTINATION_LOGIN.name,
            enterTransition = {
                when (initialState.destination.route) {
                    Destination.DESTINATION_HOME.name -> slideIntoContainer(
                        animationSpec = tween(700),
                        towards = AnimatedContentScope.SlideDirection.Start
                    )
                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    Destination.DESTINATION_HOME.name -> slideOutOfContainer(
                        animationSpec = tween(700),
                        towards = AnimatedContentScope.SlideDirection.Start
                    )
                    else -> null
                }
            }
        ) {
            LoginScreen(navigate = {
                navController.navigate(Destination.DESTINATION_HOME.name) {
//                    popUpTo(Destination.DESTINATION_LOGIN.name) {
//                        inclusive = true
//                    }
                }
            })
        }
        composable(route = Destination.DESTINATION_HOME.name) {
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