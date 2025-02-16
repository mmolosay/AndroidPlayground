package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        val focusRequester = remember { FocusRequester() }
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .focusRequester(focusRequester)
                .focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(times = 10) { index ->
                key(index) {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isFocused = interactionSource.collectIsFocusedAsState().value
                    val backgroundColor = if (isFocused) Color.Yellow else Color.LightGray
                    Box(
                        modifier = Modifier
                            .width(180.dp)
                            .height(100.dp)
                            .background(backgroundColor)
                            .focusable(interactionSource = interactionSource)
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
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