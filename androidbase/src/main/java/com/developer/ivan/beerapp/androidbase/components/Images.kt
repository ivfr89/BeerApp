package com.developer.ivan.beerapp.androidbase.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.core.graphics.drawable.toBitmap
import com.skydoves.landscapist.coil.CoilImage

private const val DEFAULT_ALPHA_DURATION = 0

@Composable
fun ImageUrlPainter(
    url: String,
    modifier: Modifier = Modifier,
    animateAlphaDuration: Int = DEFAULT_ALPHA_DURATION,
    onLoadImageError: (() -> Unit)? = null
) {
    var visible by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(animateAlphaDuration)
    )

    CoilImage(
        modifier = modifier,
        success = {
            visible = true
            it.drawable?.toBitmap()?.asImageBitmap()?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    modifier = Modifier.alpha(alpha),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            }
        },
        failure = {
            onLoadImageError?.invoke()
        },
        imageModel = url
    )
}
