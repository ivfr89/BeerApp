package com.developer.ivan.beerapp.ui.main.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.androidbase.components.DescriptionSection
import com.developer.ivan.beerapp.androidbase.components.ImageUrlPainter
import com.developer.ivan.beerapp.androidbase.components.TitleSection
import com.developer.ivan.beerapp.theme.Green_50
import com.developer.ivan.beerapp.theme.Red_60

@Composable
fun BeerDetailScreen(
    beerId: Int,
    viewModel: BeerDetailViewModel = hiltViewModel()
) {
    viewModel.getBeer(beerId)

    HandleObserverStates(
        viewModel = viewModel
    )
}

@Composable
fun HandleObserverStates(
    viewModel: BeerDetailViewModel
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is BeerDetailState.Error,
        BeerDetailState.Idle,
        BeerDetailState.Loading -> Unit
        is BeerDetailState.ShowItem -> {
            val item = (state as BeerDetailState.ShowItem).item
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colors.primaryVariant)

            ) {
                BeerInfoHeader(item.imageUrl)

                Column(modifier = Modifier.padding(16.dp)) {
                    BeerInfoTitle(
                        name = item.name,
                        tagline = item.tagline,
                        isAvailable = item.isAvailable,
                        onButtonSwitch = {
                            viewModel.switchAvailability()
                        }
                    )
                    BeerInfoSection(stringResource(id = R.string.description), item.description)
                    item.alcoholByVolume?.let {
                        BeerInfoSection(
                            stringResource(id = R.string.alcohol_by_volume),
                            it.toString()
                        )
                    }
                    item.ibu?.let {
                        BeerInfoSection(
                            stringResource(id = R.string.bitterness),
                            it.toString()
                        )
                    }
                    BeerInfoSection(
                        stringResource(id = R.string.food_pairing),
                        buildString { item.foodPairing.forEach { appendLine(it) } }
                    )
                }
            }
        }
    }
}

@Composable
private fun BeerInfoHeader(imageUrl: String?) {
    imageUrl?.let {
        Box(
            Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .background(color = MaterialTheme.colors.background)
        ) {
            ImageUrlPainter(
                modifier = Modifier
                    .height(300.dp)
                    .align(Alignment.Center),
                url = it
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun BeerInfoTitle(
    name: String,
    tagline: String?,
    isAvailable: Boolean,
    onButtonSwitch: () -> Unit
) {
    val textSwitchAvailability =
        if (isAvailable) R.string.set_not_available else R.string.set_available

    val colorSwitchAvailability =
        if (isAvailable) Red_60 else Green_50

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth()
        ) {
            Text(
                text = name,
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))

            tagline?.let {
                Text(
                    text = name,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isAvailable) {
                        Text(text = stringResource(id = R.string.available))
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp, 16.dp),
                            tint = Color.Green
                        )
                    } else {
                        Text(text = stringResource(id = R.string.not_available))
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp, 16.dp),
                            tint = Color.Red
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorSwitchAvailability),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = {
                        onButtonSwitch()
                    }
                ) {
                    Text(text = stringResource(id = textSwitchAvailability))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BeerInfoSection(title: String, content: String?) {
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TitleSection(title = title)
        Spacer(modifier = Modifier.height(16.dp))
        content?.let {
            DescriptionSection(subtitle = it)
        } ?: run {
            Text(text = stringResource(id = R.string.no_content))
        }
    }
}
