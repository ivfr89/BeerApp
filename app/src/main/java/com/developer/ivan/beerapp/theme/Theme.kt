package com.developer.ivan.beerapp.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.defaultShimmerTheme
private const val SHIMMER_ROTATION = 25f
private const val SHIMMER_DURATION_MILLIS = 1000
private const val SHIMMER_DELAY_MILLIS = 500

private val DarkColorScheme = darkColors(
    primary = Blue80,
    secondary = PurpleGrey80,
    primaryVariant = Green_70,
    surface = Gray_80
)
private val LightColorScheme = darkColors(
    primary = White_10,
    secondary = PurpleGrey80,
    primaryVariant = Black_90,
    surface = White_10,
    background = White_100
)

@Composable
fun BeerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}

val shimmerTheme = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = SHIMMER_DURATION_MILLIS,
            delayMillis = SHIMMER_DELAY_MILLIS,
            easing = LinearEasing
        )
    ),
    rotation = SHIMMER_ROTATION,
    shaderColors = listOf(
        Color.Unspecified.copy(alpha = 1.0f),
        Color.Unspecified.copy(alpha = 0.0f),
        Color.Unspecified.copy(alpha = 1.0f)
    ),
    shaderColorStops = null,
    shimmerWidth = 300.dp
)
