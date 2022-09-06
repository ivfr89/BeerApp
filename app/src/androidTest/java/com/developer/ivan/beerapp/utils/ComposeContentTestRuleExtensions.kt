package com.developer.ivan.beerapp.utils

import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.ComposeContentTestRule

fun ComposeContentTestRule.waitUntilExists(
    matcher: SemanticsMatcher,
    count: Int = 1,
    timeoutMillis: Long = 1_000L
) {
    return this.waitUntilNodeCount(matcher, count, timeoutMillis)
}

fun ComposeContentTestRule.waitUntilNodeCount(
    matcher: SemanticsMatcher,
    count: Int,
    timeoutMillis: Long = 1_000L
) {
    this.waitUntil(timeoutMillis) {
        this.onAllNodes(matcher).fetchSemanticsNodes().size == count
    }
}
