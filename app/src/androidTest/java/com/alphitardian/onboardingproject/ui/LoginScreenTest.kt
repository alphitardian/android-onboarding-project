package com.alphitardian.onboardingproject.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_LOGIN
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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

    @Test
    fun testInitialUiState() {
        val emailTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_email)),
                true)
        val passwordTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_password)),
                true)
        val button =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_button)),
                true)

        emailTextField.assertIsDisplayed()
        passwordTextField.assertIsDisplayed()
        button.assertIsDisplayed()
    }

    @Test
    fun testTextfieldTyping() {
        val emailTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_email)),
                true)
        val passwordTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_password)),
                true)

        emailTextField.apply {
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }

        passwordTextField.apply {
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }
    }

    @Test
    fun testTogglePasswordVisibility() {
        val passwordTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_password)),
                true)
        val passwordVisibilityButton =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_password_visibility_icon)),
                true)

        passwordVisibilityButton.performClick()

        passwordTextField.apply {
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }
    }

    @Test
    fun testLoginFailed() {
        val button =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_button)),
                true)
        val dialog =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_auth_dialog)))

        button.performClick()

        dialog.assertIsDisplayed()
    }

    @Test
    fun testLoginSuccess() {
        val emailTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_email)),
                true)
        val passwordTextField =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_password)),
                true)
        val button =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_login_button)),
                true)

        emailTextField.apply {
            performClick()
            performTextInput("tester")
        }

        passwordTextField.apply {
            performClick()
            performTextInput("tester123")
        }

        button.performClick()

        composeTestRule.onNode(hasText(composeTestRule.activity.getString(R.string.home_title)))
            .assertExists()
    }
}