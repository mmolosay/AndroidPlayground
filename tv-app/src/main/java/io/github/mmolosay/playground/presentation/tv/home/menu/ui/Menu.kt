package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.home.menu.MainMenuItem
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuState
import io.github.mmolosay.playground.presentation.tv.home.menu.SportMenuItem

// TODO: add shadow as in v1

@Composable
internal fun Menu(
    data: MenuData,
    modifier: Modifier = Modifier,
) {
    // TODO: rollback backgroundColor
//    val backgroundColor = when (data.menuState.isMenuOpen) {
//        true -> Color.Black
//        false -> Color.Transparent
//    }
    val backgroundColor = Color.LightGray.copy(alpha = 0.30f)
    val wholeMenuFocusRequester = remember { FocusRequester() }
    val sportMenuFocusRequester = remember { FocusRequester() }
    var doesWholeMenuHaveFocus by remember { mutableStateOf(false) }
    var doesMainMenuHaveFocus by remember { mutableStateOf(false) }
    val showSportMenu = (data.menuState.isMenuOpen && data.menuState.isSportMenuOpen)

    fun focusRequester(): FocusRequester =
        when {
            showSportMenu -> sportMenuFocusRequester
            else -> wholeMenuFocusRequester
        }

    Box(
        modifier = Modifier
            .onFocusChanged {
                data.toggleMenu(open = it.isFocused || it.hasFocus)
            }
            .focusable(),
    ) {

        Row(
            modifier = modifier
                .fillMaxHeight()
                .background(backgroundColor)
                .onFocusChanged {
                    doesWholeMenuHaveFocus = it.hasFocus
                }
                .focusRequester(wholeMenuFocusRequester)
                .focusGroup(),
        ) {
            val useAfterimageAppearance = kotlin.run {
                val isMenuOpen = data.menuState.isMenuOpen
                val isSportMenuOpen = data.menuState.isSportMenuOpen
                (isMenuOpen && isSportMenuOpen && !doesMainMenuHaveFocus)
            }
            MainMenu(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically),
                data = data.mainMenu,
                menuState = data.menuState,
                useAfterimageAppearance = useAfterimageAppearance,
                onFocusChanged = {
                    doesMainMenuHaveFocus = it.hasFocus
                    if (doesMainMenuHaveFocus && data.menuState.isSportMenuOpen && data.menuState.isMenuOpen) {
                        data.toggleSportMenu(open = false)
                    }
                },
            )

            if (showSportMenu) {
                SportMenu(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .focusRequester(sportMenuFocusRequester),
                    data = data.sportMenu,
                )
            }
        }
    }

    LaunchedEffect(data.menuState.isMenuOpen) {
        if (data.menuState.isMenuOpen) {
            focusRequester().requestFocus()
        }
    }
    LaunchedEffect(data.mainMenu.selectedItem) {
        val isSportsItemSelected = data.mainMenu.selectedItem.type == MainMenuItem.Type.Sports
        data.toggleSportMenu(open = isSportsItemSelected)
    }
}

@Preview
@Composable
private fun Preview() =
    PlaygroundTheme {
        val focusRequester = remember { FocusRequester() }
        Menu(
            modifier = Modifier.focusRequester(focusRequester),
            data = previewData(),
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

@Suppress("SpellCheckingInspection")
private fun previewData() =
    MenuData(
        mainMenu = MenuData.MainMenu(
            selectedItem = MainMenuItem(
                type = MainMenuItem.Type.Home,
                title = "Home",
            ),
            items = listOf(
                MainMenuItem(
                    type = MainMenuItem.Type.Home,
                    title = "Home",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Sports,
                    title = "Sports",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Schedule,
                    title = "Schedule",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.LiveTv,
                    title = "Live TV",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Settings,
                    title = "Settings",
                ),
            ),
            selectItem = {},
        ),
        sportMenu = MenuData.SportMenu(
            selectedItem = SportMenuItem(
                id = "boxing",
                title = "Boxing",
            ),
            items = listOf(
                SportMenuItem(
                    id = "nfl",
                    title = "NFL",
                ),
                SportMenuItem(
                    id = "boxing",
                    title = "Boxing",
                ),
                SportMenuItem(
                    id = "women-football",
                    title = "Women's Football",
                ),
                SportMenuItem(
                    id = "soccer",
                    title = "Soccer",
                ),
                SportMenuItem(
                    id = "mma",
                    title = "MMA",
                ),
                SportMenuItem(
                    id = "smth-longer",
                    title = "Something longer",
                ),
                SportMenuItem(
                    id = "smth-even-more-longer",
                    title = "Something even more longer",
                ),
            ),
        ),
        menuState = MenuState(
            isMenuOpen = true,
            isSportMenuOpen = false,
        ),
        toggleMenu = {},
        toggleSportMenu = {},
    )