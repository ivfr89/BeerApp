package com.developer.ivan.beerapp.ui.main.models

import android.os.Parcelable
import com.developer.ivan.beerapp.androidbase.utils.UniqueItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class BeerUi(
    override val id: String,
    val name: String,
    val tagline: String?,
    val description: String?,
    val imageUrl: String?,
    val alcoholByVolume: Double?,
    val ibu: Float?,
    val foodPairing: List<String>,
    val isAvailable: Boolean
) : Parcelable, UniqueItem
