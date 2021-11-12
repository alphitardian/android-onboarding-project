package com.alphitardian.onboardingproject.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.navigation.Destination
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        composeTestRule.setContent {
            OnboardingProjectTheme {
                AppNavigation(startDestination = Destination.DESTINATION_LOGIN.name)
            }
        }
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testInitialUiState() {
        val emailTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTextFieldTag), true).assertIsDisplayed()

        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).assertIsDisplayed()

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true).assertIsDisplayed()
    }

    @Test
    fun testTextfieldTyping() {
        val emailTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTextFieldTag), true).apply {
            assertIsDisplayed()
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }

        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).apply {
            assertIsDisplayed()
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }
    }

    @Test
    fun testTogglePasswordVisibility() {
        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).apply {
            assertIsDisplayed()
            performClick()
            performTextInput("tester")
            assert(hasText("tester"))
        }

        val passwordVisibilityButtonTag = composeTestRule.activity.getString(R.string.testtag_login_password_visibility_icon)
        composeTestRule.onNode(hasTestTag(passwordVisibilityButtonTag), true).performClick()
    }

    @Test
    fun testLoginFailedAuthorizationFailed() {
        val emailTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTextFieldTag), true).apply {
            performClick()
            performTextInput("tester")
        }

        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).apply {
            performClick()
            performTextInput("tester")
        }

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true).performClick()

        val dialogTag = composeTestRule.activity.getString(R.string.testtag_login_auth_dialog)
        composeTestRule.onNode(hasTestTag(dialogTag)).assertIsDisplayed()
    }

    @Test
    fun testLoginFieldEmptyPasswordField() {
        val emailTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTextFieldTag), true).apply {
            performClick()
            performTextInput("tester")
        }

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true).performClick()

        val messageTag = composeTestRule.activity.getString(R.string.testtag_login_error_message)
        composeTestRule.onNode(hasTestTag(messageTag), true).assertIsDisplayed()
    }

    @Test
    fun testLoginFieldEmptyEmailField() {
        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).apply {
            performClick()
            performTextInput("tester")
        }

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true).performClick()

        val messageTag = composeTestRule.activity.getString(R.string.testtag_login_error_message)
        composeTestRule.onNode(hasTestTag(messageTag), true).assertIsDisplayed()
    }

    @Test
    fun testLoginSuccess() {
        val emailTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTextFieldTag), true).apply {
            performClick()
            performTextInput("tester")
        }

        val passwordTextFieldTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTextFieldTag), true).apply {
            performClick()
            performTextInput("tester123")
        }

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true).performClick()

        val homeTitleTag = composeTestRule.activity.getString(R.string.home_title)
        composeTestRule.onNode(hasText(homeTitleTag)).assertExists()
    }
}