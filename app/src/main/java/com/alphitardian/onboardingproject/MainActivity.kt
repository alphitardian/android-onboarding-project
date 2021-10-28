package com.alphitardian.onboardingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_HOME
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_LOGIN
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val datastore = PrefStore(this)

        CoroutineScope(Dispatchers.Main).launch {
            val savedToken = datastore.userToken.first().toString()

            if (savedToken.isNotEmpty()) {
                initUI(DESTINATION_HOME)
            } else {
                initUI(DESTINATION_LOGIN)
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
