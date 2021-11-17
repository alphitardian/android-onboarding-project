package com.alphitardian.onboardingproject.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.datastore.PrefStore
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.navigation.Destination
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.*

@ExperimentalAnimationApi
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        runBlocking {
            val datastore = PrefStore(composeTestRule.activity.applicationContext)
            datastore.clear()
        }
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

    private fun getUserLoggedIn() {
        runBlocking {
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

            delay(1000)

            val homeTag = composeTestRule.activity.getString(R.string.testtag_home_content)
            composeTestRule.onNode(hasTestTag(homeTag)).assertExists()
        }
    }

    @Test
    fun testTopBarData() {
        runBlocking {
            val userFullNameTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_fullname)
            composeTestRule.onNode(hasTestTag(userFullNameTag), true)
                .assertIsDisplayed()

            val userBioTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_bio)
            composeTestRule.onNode(hasTestTag(userBioTag), true)
                .assertIsDisplayed()

            val userWebTag = composeTestRule.activity.getString(R.string.testtag_home_topbar_web)
            composeTestRule.onNode(hasTestTag(userWebTag), true)
                .assertIsDisplayed()

            delay(1000)
        }
    }

    @Test
    fun testNewsContent() {
        runBlocking {
            val homeTag = composeTestRule.activity.getString(R.string.testtag_home_content)
            composeTestRule.onNode(hasTestTag(homeTag)).assertExists()

            val newsTitleTag = composeTestRule.activity.getString(R.string.testtag_home_news_title)
            composeTestRule.onAllNodesWithTag(newsTitleTag, true)
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
}