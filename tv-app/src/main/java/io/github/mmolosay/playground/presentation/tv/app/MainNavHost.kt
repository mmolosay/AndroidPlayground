package io.github.mmolosay.playground.presentation.tv.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuViewModel
import io.github.mmolosay.playground.presentation.tv.home.menu.ui.MenuScreen
import io.github.mmolosay.playground.presentation.tv.screen.FocusTestsScreen
import io.github.mmolosay.playground.presentation.tv.screen.home.HomeScreen
import io.github.mmolosay.playground.presentation.tv.screen.home.HomeViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = "menu",
    ) {
        composable(route = "home") {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                viewModel = viewModel,
            )
        }
        composable(route = "menu") {
            val viewModel = hiltViewModel<MenuViewModel>()
            MenuScreen(
                viewModel = viewModel,
                appNavController = navController,
            )
        }
        composable(route = "focus_tests") {
            FocusTestsScreen(
                appNavController = navController,
            )
        }
    }
}