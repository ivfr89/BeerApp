package com.developer.ivan.beerapp.ui.main

import BeerListScreen
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.developer.ivan.beerapp.R
import com.developer.ivan.beerapp.theme.BeerAppTheme
import com.developer.ivan.beerapp.ui.main.screens.detail.BeerDetailScreen
import com.developer.ivan.beerapp.androidbase.navigation.Feature
import com.developer.ivan.beerapp.androidbase.navigation.NavArg
import com.developer.ivan.beerapp.androidbase.navigation.NavCommand
import com.developer.ivan.beerapp.androidbase.navigation.NavItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

    @Composable
    fun BeerApp(navController: NavHostController = rememberNavController()) {

        val systemUiController = rememberSystemUiController()
        val colorForSystemUi = MaterialTheme.colors.primaryVariant

        val currentRoute: String =
            navController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()

        val showUpNavigation: Boolean =
            !NavItem.values().map { it.navCommand.route }.contains(currentRoute)

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Toolbar(showUpNavigation, navController)
                }

            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController)
                }
            }
        )

        SideEffect {
            systemUiController.setSystemBarsColor(color = colorForSystemUi)
        }
    }

    @Composable
    private fun Toolbar(
        showUpNavigation: Boolean,
        navController: NavHostController
    ) {
        if (showUpNavigation) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        } else {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.beer_list)
            )
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = Feature.BEER.route
        ) {
            beerGraph(navController)
        }
    }

    private fun NavGraphBuilder.beerGraph(navController: NavController) {
        navigation(
            startDestination = NavCommand.ContentType(Feature.BEER).route,
            route = Feature.BEER.route
        ) {
            composable(NavCommand.ContentType(Feature.BEER)) {
                BeerListScreen(
                    onNavigateToBeerDetail = { id ->
                        navController.navigate(
                            NavCommand.ContentTypeDetail(Feature.BEER).createRoute(id)
                        )
                    }
                )
            }

            composable(NavCommand.ContentTypeDetail(Feature.BEER)) {
                it.arguments?.getInt(NavArg.ItemId.key)?.let { id ->
                    BeerDetailScreen(id)
                }
            }
        }
    }

    private fun NavGraphBuilder.composable(
        navCommand: NavCommand,
        content: @Composable (NavBackStackEntry) -> Unit
    ) {
        composable(
            route = navCommand.route,
            arguments = navCommand.args
        ) {
            content(it)
        }
    }
}
