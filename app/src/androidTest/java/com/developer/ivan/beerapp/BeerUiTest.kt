package com.developer.ivan.beerapp

import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.developer.ivan.beerapp.androidbase.theme.BeerAppTheme
import com.developer.ivan.beerapp.fake.beerFakeList
import com.developer.ivan.beerapp.ui.main.BeerApp
import com.developer.ivan.beerapp.ui.main.MainActivity
import com.developer.ivan.beerapp.ui.main.screens.list.BEER_LIST_TAG
import com.developer.ivan.beerapp.utils.waitUntilExists
import com.developer.ivan.domain.Beer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BeerUiTest {

    @get:Rule(order = 0)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            BeerAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    BeerApp()
                }
            }
        }
        beerFakeList = (0..3).map {
            Beer(
                id = it,
                name = "Beer $it",
                tagline = "Beer $it",
                description = "Description for beer $it",
                imageUrl = "http://fakebeer.com/img1.jpg",
                alcoholByVolume = 0.0,
                ibu = 0f,
                foodPairing = emptyList(),
                isAvailable = true
            )
        }
    }

    @Test
    fun displayMainList() {
        composeTestRule.onNodeWithTag(BEER_LIST_TAG).assertIsDisplayed()

        beerFakeList.forEach {
            composeTestRule.onNodeWithText(it.name).assertExists()
        }
    }

    @Test
    fun goToDetailScreenAfterClickOnFirstListItem() {
        val expectedItem = beerFakeList.first()

        composeTestRule.onNodeWithTag(BEER_LIST_TAG).assertIsDisplayed()

        composeTestRule.waitUntilExists(hasText(expectedItem.name))

        composeTestRule.onNodeWithText(expectedItem.name).performClick()

        composeTestRule.waitUntilExists(hasText(expectedItem.name), count = 2)

        composeTestRule.onAllNodesWithText(expectedItem.name).assertAny(hasText(expectedItem.name))
    }

    @Test
    fun switchAvailabilityShouldChangeMessageToNotAvailable() {
        val expectedItem = beerFakeList.first()

        composeTestRule.onNodeWithTag(BEER_LIST_TAG).assertIsDisplayed()

        composeTestRule.waitUntilExists(hasText(expectedItem.name))

        composeTestRule.onNodeWithText(expectedItem.name).performClick()

        composeTestRule.waitUntilExists(hasText(expectedItem.name), count = 2)

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.set_not_available))
            .performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.not_available))
            .assertIsDisplayed()
    }

    @Test
    fun switchNotAvailabilityShouldChangeMessageToAvailable() {
        beerFakeList =
            beerFakeList.mapIndexed { index, beer -> if (index == 0) beer.copy(isAvailable = false) else beer }
        val expectedItem = beerFakeList.first()

        composeTestRule.onNodeWithTag(BEER_LIST_TAG).assertIsDisplayed()

        composeTestRule.waitUntilExists(hasText(expectedItem.name))

        composeTestRule.onNodeWithText(expectedItem.name).performClick()

        composeTestRule.waitUntilExists(hasText(expectedItem.name), count = 2)

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.set_available))
            .performClick()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.available))
            .assertIsDisplayed()
    }
}
