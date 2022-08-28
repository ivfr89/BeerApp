package com.developer.ivan.beerapp.ui.main.models

import android.os.Parcelable
import com.developer.ivan.beerapp.ui.utils.UniqueItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeerUi(
    override val id: String,
    val name: String,
    val tagline: String?,
    val description: String?,
    val image_url: String?,
    val alcoholByVolume: Double?,
    val ibu: Float?,
    val foodPairing: List<String>,
    val isAvailable: Boolean
) : Parcelable, UniqueItem
