package com.alphitardian.onboardingproject.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_LOGIN
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        composeTestRule.setContent {
            OnboardingProjectTheme {
                AppNavigation(startDestination = DESTINATION_LOGIN)
            }
        }
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}