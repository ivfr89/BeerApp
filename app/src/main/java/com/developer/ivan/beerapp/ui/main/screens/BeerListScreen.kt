import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.theme.Red_60
import com.developer.ivan.beerapp.theme.shimmerTheme
import com.developer.ivan.beerapp.ui.main.BeerListState
import com.developer.ivan.beerapp.ui.main.screens.viewmodels.BeerListViewModel
import com.developer.ivan.beerapp.ui.main.mapLazyType
import com.developer.ivan.beerapp.ui.main.models.BeerUi
import com.developer.ivan.beerapp.ui.utils.InfiniteListHandler
import com.developer.ivan.beerapp.ui.utils.LazyColumnPaginable
import com.skydoves.landscapist.coil.CoilImage
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

private const val DEFAULT_ALPHA_DURATION = 0

@Composable
fun BeerListScreen(
    viewModel: BeerListViewModel = hiltViewModel(),
    onNavigateToBeerDetail: (id: Int) -> Unit,
) {
    var forceReload by rememberSaveable { mutableStateOf(true) }

    viewModel.getBeers(fromStart = forceReload, force = forceReload)

    HandleObserverStates(
        viewModel = viewModel,
        onNavigateToBeerDetail = {
            onNavigateToBeerDetail(it)
            forceReload = false
        }
    )
}

@Composable
fun HandleObserverStates(
    viewModel: BeerListViewModel,
    onNavigateToBeerDetail: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val lazyState = rememberLazyListState()

    InfiniteListHandler(
        listState = lazyState,
        isLoading = state is BeerListState.Paging
    ) {
        viewModel.loadMore()
    }

    when (state) {
        BeerListState.Idle -> Unit
        is BeerListState.Error -> Unit
        BeerListState.Loading,
        is BeerListState.Paging,
        is BeerListState.WithItems -> {
            LazyColumnPaginable<BeerUi>(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                lazyState = lazyState,
                lazyType = mapLazyType(state),
                contentSkeleton = {
                    SmallCardSkeleton(
                        Modifier
                            .padding(8.dp)
                            .height(80.dp)
                    )
                },
                loader = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center),
                            color = MaterialTheme.colors.primaryVariant,
                            strokeWidth = 2.dp
                        )
                    }
                }
            ) {
                SmallCard(
                    Modifier
                        .padding(8.dp, vertical = 4.dp)
                        .height(80.dp),
                    title = it.name,
                    subtitle = it.tagline.orEmpty(),
                    url = it.imageUrl.orEmpty(),
                    color = it.takeIf { it.isAvailable }?.let { MaterialTheme.colors.surface }
                        ?: run { Red_60 },
                    onClick = { onNavigateToBeerDetail(it.id.toInt()) }
                )
            }
        }
    }
}

@Composable
fun SmallCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    url: String,
    color: Color,
    @DrawableRes iconDrawableRes: Int = R.drawable.ic_beer,
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