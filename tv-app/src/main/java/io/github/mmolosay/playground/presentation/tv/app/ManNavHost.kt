package io.github.mmolosay.playground.presentation.tv.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.mmolosay.playground.presentation.tv.home.HomeScreen
import io.github.mmolosay.playground.presentation.tv.home.HomeViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "home",
    ) {
        composable(route = "home") {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
            )
        }
        composable(route = "settings") {
            // This destination exists to demonstrate possible navigation structure
        }
    }
}