@file:Suppress("ktlint:filename")

package com.developer.ivan.beerapp.androidbase.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun Toolbar(
    showUpNavigation: Boolean,
    navController: NavHostController,
    text: String
) {
    if (showUpNavigation) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
        }
    } else {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = text
        )
    }
}
