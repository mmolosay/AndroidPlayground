package io.github.mmolosay.playground.presentation.tv.menu.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.mmolosay.playground.presentation.tv.common.FocusableElement

@Composable
internal fun MenuNavHost(
    appNavController: NavController,
    menuNavController: NavHostController,
    menuState: MenuState,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = menuNavController,
        startDestination = MenuDest.Home.route,
        route = "HOME", // as in v1 // TODO: try different value
    ) {
        home(
            appNavController = appNavController,
            menuState = menuState,
        )
    }
}

enum class MenuDest(val route: String) {
    Home("home"),
}

@OptIn(ExperimentalComposeUiApi::class)
private fun NavGraphBuilder.home(
    appNavController: NavController,
    menuState: MenuState,
) =
    composable(route = MenuDest.Home.route) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val focusRequester = remember { FocusRequester() }
            FocusableElement(
                modifier = Modifier
                    .focusRequester(focusRequester),
                onClick = { appNavController.navigate("focus_tests") },
            ) {
                Text(
                    text = "Go to focus tests",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            BackHandler(
                enabled = !menuState.isMenuOpen.value,
            ) {
                menuState.toggleMenu(open = true)
            }
        }
    }