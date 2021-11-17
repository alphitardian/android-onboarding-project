package com.alphitardian.onboardingproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.navigation.Destination
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalAnimationApi
class HomeScreenTest {

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
        getUserLoggedIn()
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testHomeScreen() {
        testInitialUiState()
        testTopBarData()
        testNewsContent()
        testScrollNewsContent()
    }

    private fun getUserLoggedIn() {
        val emailTag = composeTestRule.activity.getString(R.string.testtag_login_email)
        composeTestRule.onNode(hasTestTag(emailTag), true)
            .performClick()
            .performTextInput("tester")

        val passwordTag = composeTestRule.activity.getString(R.string.testtag_login_password)
        composeTestRule.onNode(hasTestTag(passwordTag), true)
            .performClick()
            .performTextInput("tester123")

        val buttonTag = composeTestRule.activity.getString(R.string.testtag_login_button)
        composeTestRule.onNode(hasTestTag(buttonTag), true)
            .performClick()
    }

    private fun testInitialUiState() {
        val topBarTag = composeTestRule.activity.getString(R.string.testtag_home_topbar)
        composeTestRule.onNode(hasTestTag(topBarTag), true).assertIsDisplayed()

        val homeTitleTag = composeTestRule.activity.getString(R.string.home_title)
        composeTestRule.onNode(hasTestTag(homeTitleTag), true).assertExists()

        val homeDescTag = composeTestRule.activity.getString(R.string.home_sub_title)
        composeTestRule.onNode(hasTestTag(homeDescTag), true).assertExists()
    }

    private fun testTopBarData() {
        runBlocking {
            val profilePictureTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_profile_picture)
            composeTestRule.onNode(hasTestTag(profilePictureTag), true).assertExists()

            val userFullNameTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_fullname)
            composeTestRule.onNode(hasTestTag(userFullNameTag), true)
                .assertIsDisplayed()
                .assert(hasText("Prapto Prawirodirjo"))

            val userBioTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_bio)
            composeTestRule.onNode(hasTestTag(userBioTag), true)
                .assertIsDisplayed()
                .assert(hasText("5 terlalu banyak, 10 kurang, lorem ipsum dolor si jamet \uD83C\uDFB8"))

            val userWebTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_web)
            composeTestRule.onNode(hasTestTag(userWebTag), true)
                .assertIsDisplayed()
                .assert(hasText("https://icehousecorp.com"))

            delay(1000)
        }
    }

    private fun testNewsContent() {
        runBlocking {
            val newsImageTag = composeTestRule.activity.getString(R.string.testtag_home_news_image)
            composeTestRule.onAllNodes(hasTestTag(newsImageTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsTitleTag = composeTestRule.activity.getString(R.string.testtag_home_news_title)
            composeTestRule.onAllNodes(hasTestTag(newsTitleTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsCategoryTag = composeTestRule.activity.getString(R.string.testtag_home_news_category)
            composeTestRule.onAllNodes(hasTestTag(newsCategoryTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsDateTag = composeTestRule.activity.getString(R.string.testtag_home_news_date)
            composeTestRule.onAllNodes(hasTestTag(newsDateTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsViewCounterTag = composeTestRule.activity.getString(R.string.testtag_home_news_view)
            composeTestRule.onAllNodes(hasTestTag(newsViewCounterTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsCommentCounterTag = composeTestRule.activity.getString(R.string.testtag_home_news_comment)
            composeTestRule.onAllNodes(hasTestTag(newsCommentCounterTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsLikeCounterTag = composeTestRule.activity.getString(R.string.testtag_home_news_like)
            composeTestRule.onAllNodes(hasTestTag(newsLikeCounterTag), true)
                .onFirst()
                .assertIsDisplayed()

            val newsDislikeCounterTag = composeTestRule.activity.getString(R.string.testtag_home_news_dislike)
            composeTestRule.onAllNodes(hasTestTag(newsDislikeCounterTag), true)
                .onFirst()
                .assertIsDisplayed()

            delay(1000)
        }
    }

    private fun testScrollNewsContent() {
        Thread.sleep(1000)

        val newsContentTag = composeTestRule.activity.getString(R.string.testtag_home_news_content)
        composeTestRule.onNode(hasTestTag(newsContentTag))
            .performGesture {
                swipe(
                    start = this.center,
                    end = Offset(this.center.x, this.center.y - 1000),
                    durationMillis = 1000
                )
            }
    }
}