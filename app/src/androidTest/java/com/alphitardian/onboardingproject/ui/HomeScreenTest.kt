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

    fun getUserLoggedIn() {
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
    }

    @Test
    fun testInitialUiState() {
        val topBar =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_topbar)),
                true)
        val newsContent =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_content)),
                true)

        topBar.assertIsDisplayed()
        newsContent.assertIsDisplayed()
    }

    @Test
    fun testTopBarData() {
        val profilePicture =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_topbar_profile_picture)),
                true)
        val userFullName =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_topbar_fullname)),
                true)
        val userBio =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_topbar_bio)),
                true)
        val userWeb =
            composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_topbar_web)),
                true)

        profilePicture.assertIsDisplayed()

        userFullName.apply {
            assertIsDisplayed()
            assert(hasText("Prapto Prawirodirjo"))
        }

        userBio.apply {
            assertIsDisplayed()
            assert(hasText("5 terlalu banyak, 10 kurang, lorem ipsum dolor si jamet \uD83C\uDFB8"))
        }

        userWeb.apply {
            assertIsDisplayed()
            assert(hasText("https://icehousecorp.com"))
        }
    }

    @Test
    fun testNewsContent() {
        val newsImage =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_image)),
                true).onFirst()
        val newsTitle =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_title)),
                true).onFirst()
        val newsCategory =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_category)),
                true).onFirst()
        val newsDate =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_date)),
                true).onFirst()
        val newsViewCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_view)),
                true).onFirst()
        val newsCommentCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_comment)),
                true).onFirst()
        val newsLikeCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_like)),
                true).onFirst()
        val newsDislikeCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_dislike)),
                true).onFirst()

        newsImage.assertIsDisplayed()
        newsTitle.assertIsDisplayed()
        newsCategory.assertIsDisplayed()
        newsDate.assertIsDisplayed()
        newsViewCounter.assertIsDisplayed()
        newsCommentCounter.assertIsDisplayed()
        newsLikeCounter.assertIsDisplayed()
        newsDislikeCounter.assertIsDisplayed()
    }

    @ExperimentalTestApi
    @Test
    fun testScrollNewsContent() {
        composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_content)))
            .performGesture {
                swipe(
                    start = this.center,
                    end = Offset(this.center.x, this.center.y - 1000),
                    durationMillis = 1000
                )
            }

        Thread.sleep(2000) // simulate reading news

        composeTestRule.onNode(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_content)))
            .performScrollToIndex(0)
    }
}