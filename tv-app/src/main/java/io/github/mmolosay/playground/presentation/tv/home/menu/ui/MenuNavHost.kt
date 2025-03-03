package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
    menuState: MenuState,
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = MenuDest.Home.route,
        route = "HOME", // as in v1 // TODO: try different value
    ) {
        home(
            menuState = menuState,
        )
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RailOfTiles(
    modifier: Modifier,
    focusRequester: FocusRequester,
    title: String,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(Modifier.height(16.dp))
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
                            .focusable(interactionSource = interactionSource),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "$index",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun NavGraphBuilder.home(
    menuState: MenuState,
) =
    composable(route = MenuDest.Home.route) {
        // TODO: implement
//        Box {
//            val focusRequesters = remember { mutableStateListOf<FocusRequester>() }
//            Column {
//                repeat(times = 4) { index ->
//                    key(index) {
//                        val focusRequester = remember { FocusRequester() }
//                        LaunchedEffect(Unit) {
//                            focusRequesters += focusRequester
//                        }
//                        RailOfTiles(
//                            modifier = Modifier
//                                .padding(start = menuState.collapsedMenuWidth.value ?: 0.dp)
//                                .padding(start = 16.dp),
//                            focusRequester = focusRequester,
//                            title = "Rail $index",
//                        )
//                    }
//                }
//            }
//
//            LaunchedEffect(Unit) {
//                focusRequesters.first().requestFocus()
//            }
//        }

        Box(
            modifier = Modifier
                .fillMaxSize()
//                .focusGroup()
            ,
            contentAlignment = Alignment.Center,
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val isFocused = interactionSource.collectIsFocusedAsState().value
            val backgroundColor = if (isFocused) Color.Yellow else Color.LightGray
            val focusRequester = remember { FocusRequester() }
            Box(
                modifier = Modifier
                    .width(180.dp)
                    .height(100.dp)
                    .background(backgroundColor)
                    .focusRequester(focusRequester)
                    .focusable(interactionSource = interactionSource),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Focusable element",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
//            BackHandler {
//                menuState.toggleMenu(open = true)
//            }
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