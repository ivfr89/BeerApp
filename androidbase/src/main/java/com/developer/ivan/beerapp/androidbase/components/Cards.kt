package com.developer.ivan.beerapp.androidbase.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.developer.ivan.beerapp.androidbase.theme.shimmerTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer


@Composable
fun SmallCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    url: String,
    color: Color,
    @DrawableRes iconDrawableRes: Int,
    onClick: (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    var imageLoadError by remember { mutableStateOf(false) }
    Card(modifier = modifier, elevation = 2.dp, backgroundColor = color) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onClick?.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (imageLoadError)
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = iconDrawableRes),
                    contentDescription = null
                )
            else {
                ImageUrlPainter(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    url = url,
                    onLoadImageError = {
                        imageLoadError = true
                    },
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }
}

@Composable
fun SmallCardSkeleton(
    modifier: Modifier = Modifier
) {
    val colorSkeleton = MaterialTheme.colors.surface
    val shimmerInstance =
        rememberShimmer(
            shimmerBounds = ShimmerBounds.Window,
            theme = shimmerTheme
        )

    Box {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .shimmer(shimmerInstance)
                    .background(color = colorSkeleton)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Box(
                    modifier = Modifier
                        .size(166.dp, 20.dp)
                        .shimmer(shimmerInstance)
                        .background(color = colorSkeleton)
                )
                Spacer(Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(87.dp, 12.dp)
                        .shimmer(shimmerInstance)
                        .background(color = colorSkeleton)
                )
            }
        }
    }
}
