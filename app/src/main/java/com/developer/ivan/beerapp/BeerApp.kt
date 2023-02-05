package com.developer.ivan.beerapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.developer.ivan.beerapp.androidbase.components.Toolbar
import com.developer.ivan.beerapp.androidbase.navigation.Feature
import com.developer.ivan.beerapp.androidbase.navigation.NavArg
import com.developer.ivan.beerapp.androidbase.navigation.NavCommand
import com.developer.ivan.beerapp.androidbase.navigation.NavItem
import com.developer.ivan.ui.main.screens.detail.BeerDetailScreen
import com.developer.ivan.ui.main.screens.list.BeerListScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
            ) {
                Toolbar(showUpNavigation, navController, stringResource(id = R.string.beer_list))
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
