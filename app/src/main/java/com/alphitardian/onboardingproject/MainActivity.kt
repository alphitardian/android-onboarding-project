package com.alphitardian.onboardingproject

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.navigation.Destination
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalAnimationApi
@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var datastore: PrefStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            val savedToken = datastore.userToken.first().toString()

            if (savedToken.isNotEmpty()) {
                initUI(Destination.DESTINATION_HOME.name)
            } else {
                initUI(Destination.DESTINATION_LOGIN.name)
            }
        }
    }

    private fun initUI(navDestination: String) {
        setContent {
            OnboardingProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    AppNavigation(startDestination = navDestination)
                }
            }
        }
    }
}
