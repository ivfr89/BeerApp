package com.developer.ivan.beerapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.developer.ivan.beerapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var nav: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        nav = findNavController(R.id.navigation)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    override fun onSupportNavigateUp(): Boolean {
        return (nav.navigateUp()
                || super.onSupportNavigateUp())
    }


}
