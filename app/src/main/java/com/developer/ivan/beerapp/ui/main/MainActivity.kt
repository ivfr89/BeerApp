package com.developer.ivan.beerapp.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.developer.ivan.beerapp.androidbase.theme.BeerAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overlayFixForMiUIDevices()

        setContent {
            BeerAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    BeerApp()
                }
            }
        }
    }

    private fun overlayFixForMiUIDevices() {
        lifecycleScope.launch {
            delay(300)
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }
}
