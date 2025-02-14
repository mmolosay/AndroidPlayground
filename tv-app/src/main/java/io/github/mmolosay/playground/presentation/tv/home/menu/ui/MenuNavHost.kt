package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
internal fun MenuNavHost(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = MenuDest.Home.route,
        route = "HOME", // as in v1 // TODO: try different value
    ) {
        home()
        sports()
        schedule()
        liveTv()
        settings()
    }
}

enum class MenuDest(val route: String) {
    Home("home"),
    Sports("sports"),
    Schedule("schedule"),
    LiveTv("live-tv"),
    Settings("settings"),
}

private fun NavGraphBuilder.home() =
    composable(route = MenuDest.Home.route) {
        // TODO: implement
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            val interactionSource = remember { MutableInteractionSource() }
            val backgroundColor = when (interactionSource.collectIsFocusedAsState().value) {
                true -> Color.Yellow
                false -> Color.DarkGray
            }
            val focusRequester = remember { FocusRequester() }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .background(backgroundColor)
                    .focusRequester(focusRequester)
                    .focusable(interactionSource = interactionSource)
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }

private fun NavGraphBuilder.sports() =
    composable(route = MenuDest.Sports.route) {
        // TODO: implement
    }

private fun NavGraphBuilder.schedule() =
    composable(route = MenuDest.Schedule.route) {
        // TODO: implement
    }

private fun NavGraphBuilder.liveTv() =
    composable(route = MenuDest.LiveTv.route) {
        // TODO: implement
    }

private fun NavGraphBuilder.settings() =
    composable(route = MenuDest.Settings.route) {
        // TODO: implement
    }