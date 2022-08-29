package com.developer.ivan.beerapp.androidbase.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TitleSection(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}

@Composable
fun DescriptionSection(modifier: Modifier = Modifier, subtitle: String) {
    Text(
        text = subtitle,
        modifier = modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.subtitle2,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Start
    )
}
