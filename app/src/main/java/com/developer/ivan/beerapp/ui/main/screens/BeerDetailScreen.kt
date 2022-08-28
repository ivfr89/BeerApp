package com.developer.ivan.beerapp.ui.main.screens


import ImageUrlPainter
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.ui.main.BeerDetailState
import com.developer.ivan.beerapp.ui.main.fragments.BeerDetailViewModel

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
        BeerDetailState.IsLoading -> Unit
        is BeerDetailState.ShowItem -> {
            val item = (state as BeerDetailState.ShowItem).item
            Column {
                BeerInfoTitle(
                    name = item.name,
                    imageUrl = item.image_url,
                    tagline = item.tagline,
                    isAvailable = item.isAvailable,
                    onButtonSwitch = {
                        viewModel.switchAvailability()
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
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


@Composable
fun BeerInfoTitle(name: String,
                  imageUrl: String?,
                  tagline: String?,
                  isAvailable: Boolean,
                  onButtonSwitch: ()-> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Column(modifier = Modifier
            .align(Alignment.CenterVertically)
            .fillMaxWidth()) {
            imageUrl?.let {
                ImageUrlPainter(
                    modifier = Modifier
                        .height(300.dp)
                        .align(Alignment.CenterHorizontally),
                    url = imageUrl
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                color = MaterialTheme.colors.surface,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(8.dp))

            tagline?.let {
                Text(
                    text = name,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = MaterialTheme.colors.surface,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                Row(verticalAlignment = Alignment.Bottom) {
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

                if(isAvailable) {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = {
                        onButtonSwitch()
                    }) {
                        Text(text = stringResource(id = R.string.set_not_available))
                    }
                } else {
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedButton(onClick = {
                        onButtonSwitch()
                    }) {
                        Text(text = stringResource(id = R.string.set_available))
                    }
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
            .padding(16.dp)
    ) {
        TitleSection(title = title)
        Spacer(modifier = Modifier.height(16.dp))
        content?.let {
            DescriptionSection(it)
        } ?: run {
            Text(text = stringResource(id = R.string.no_content))
        }

    }
}

@Composable
fun TitleSection(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Start
    )
}

@Composable
fun DescriptionSection(subtitle: String) {
    Text(
        text = subtitle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 0.dp, 0.dp),
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.subtitle2,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Start
    )
}
