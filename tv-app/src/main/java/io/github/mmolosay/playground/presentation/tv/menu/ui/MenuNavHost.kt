package io.github.mmolosay.playground.presentation.tv.menu.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.mmolosay.playground.presentation.tv.common.ButtonDefaultsUtil.focusAwareContainerColor
import io.github.mmolosay.playground.presentation.tv.screen.RailsScreen

@Composable
internal fun MenuNavHost(
    appNavController: NavController,
    menuNavController: NavHostController,
    menuState: MenuState,
    contentFocusRequester: ContentFocusRequester,
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = menuNavController,
        startDestination = "home",
        route = "HOME", // as in v1 // TODO: try different value
    ) {
        home(
            appNavController = appNavController,
            menuNavController = menuNavController,
            contentFocusRequester = contentFocusRequester,
            menuState = menuState,
        )
        rails(
            menuNavController = menuNavController,
            menuState = menuState,
            contentFocusRequester = contentFocusRequester,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun NavGraphBuilder.home(
    appNavController: NavController,
    menuNavController: NavController,
    contentFocusRequester: ContentFocusRequester,
    menuState: MenuState,
) =
    composable(route = "home") {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
                .focusRequester(contentFocusRequester.focusRequester)
                .focusRestorer {
                    FocusRequester.Default
                }
                .focusGroup(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val b1InteractionSource = remember { MutableInteractionSource() }
            Button(
                modifier = Modifier,
                onClick = { appNavController.navigate("focus_tests") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = focusAwareContainerColor(b1InteractionSource),
                ),
                interactionSource = b1InteractionSource,
            ) {
                Text(
                    text = "Go to focus tests",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            val b2InteractionSource = remember { MutableInteractionSource() }
            Button(
                modifier = Modifier,
                onClick = { menuNavController.navigate("rails") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = focusAwareContainerColor(b2InteractionSource),
                ),
                interactionSource = b2InteractionSource,
            ) {
                Text(
                    text = "Go to rails",
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

private fun NavGraphBuilder.rails(
    menuNavController: NavController,
    menuState: MenuState,
    contentFocusRequester: ContentFocusRequester,
) =
    composable(route = "rails") {
        RailsScreen(
            contentFocusRequester = contentFocusRequester,
            railPadding = PaddingValues(start = menuState.collapsedMenuWidth.value ?: 0.dp),
        )

        BackHandler(
            enabled = !menuState.isMenuOpen.value,
        ) {
            menuState.toggleMenu(open = true)
        }
    }