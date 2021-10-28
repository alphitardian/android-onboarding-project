package com.alphitardian.onboardingproject.ui

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.IdlingRegistry
import com.alphitardian.onboardingproject.MainActivity
import com.alphitardian.onboardingproject.R
import com.alphitardian.onboardingproject.common.Constant.DESTINATION_HOME
import com.alphitardian.onboardingproject.common.EspressoIdlingResource
import com.alphitardian.onboardingproject.navigation.AppNavigation
import com.alphitardian.onboardingproject.ui.theme.OnboardingProjectTheme
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
        composeTestRule.setContent {
            OnboardingProjectTheme {
                AppNavigation(startDestination = DESTINATION_HOME)
            }
        }
    }

    @After
    fun teardown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
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

        userFullName.assertIsDisplayed()
        userFullName.assert(hasText("Prapto Prawirodirjo"))

        userBio.assertIsDisplayed()
        userBio.assert(hasText("5 terlalu banyak, 10 kurang, lorem ipsum dolor si jamet \uD83C\uDFB8"))

        userWeb.assertIsDisplayed()
        userWeb.assert(hasText("https://icehousecorp.com"))
    }

    @Test
    fun testNewsContent() {
        val newsImage =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_image)),
                true)
        val newsTitle =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_title)),
                true)
        val newsCategory =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_category)),
                true)
        val newsDate =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_date)),
                true)
        val newsViewCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_view)),
                true)
        val newsCommentCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_comment)),
                true)
        val newsLikeCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_like)),
                true)
        val newsDislikeCounter =
            composeTestRule.onAllNodes(hasTestTag(composeTestRule.activity.getString(R.string.testtag_home_news_dislike)),
                true)

        newsImage[0].assertIsDisplayed()
        newsTitle[0].assertIsDisplayed()
        newsCategory[0].assertIsDisplayed()
        newsDate[0].assertIsDisplayed()
        newsViewCounter[0].assertIsDisplayed()
        newsCommentCounter[0].assertIsDisplayed()
        newsLikeCounter[0].assertIsDisplayed()
        newsDislikeCounter[0].assertIsDisplayed()
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