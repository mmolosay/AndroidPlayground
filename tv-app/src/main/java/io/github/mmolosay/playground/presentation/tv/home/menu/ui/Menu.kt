package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.home.menu.MainMenuItem
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.home.menu.SportMenuItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun Menu(
    data: MenuData,
    menuState: MutableMenuState,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val mainMenuFocusRequester = remember { FocusRequester() }
    val sportMenuFocusRequester = remember { FocusRequester() }
    val composeSportMenu = (data.isMenuOpen && data.sportMenu.isOpen)

    fun focusRequester(): FocusRequester =
        when {
            composeSportMenu -> sportMenuFocusRequester
            else -> mainMenuFocusRequester
        }

    Box(
        modifier = modifier
            .background(Color.Black)
            .onFocusChanged {
                data.toggleMenu(open = it.hasFocus)
            }
            .focusGroup(),
    ) {
        Row(
            modifier = Modifier
//                .animateMenuShadow(isMenuOpen = data.isMenuOpen)
                .animateContentSize() ,
        ) {
            val uiMainMenuItems = data.mainMenu.items.map { item ->
                UiMainMenuItem(
                    icon = item.type.icon(),
                    title = item.title,
                    isSelected = (item == data.mainMenu.selectedItem),
                    onClick = {
                        data.mainMenu.selectItem(item)
                        val isSportsItem = (item.type == MainMenuItem.Type.Sports)
                        data.toggleSportMenu(open = isSportsItem)
                    },
                )
            }
            MainMenu(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .onSizeChanged { size ->
                        if (!data.isMenuOpen) {
                            val dpWidth = with(density) { size.width.toDp() }
                            menuState.collapsedMenuWidth.value = dpWidth
                        }
                    }
                    .padding(horizontal = 6.dp)
                    .focusProperties {
                        enter = { mainMenuFocusRequester }
                    },
                items = uiMainMenuItems,
                selectedItemFocusRequester = mainMenuFocusRequester,
                useAfterimageAppearance = (data.isMenuOpen && data.sportMenu.isOpen),
                useCollapsedAppearance = !data.isMenuOpen,
                onFocusChanged = {
                    if (it.hasFocus && data.sportMenu.isOpen && data.isMenuOpen) {
                        data.toggleSportMenu(open = false)
                    }
                },
            )

            if (composeSportMenu) {
                val uiSportMenuItems = data.sportMenu.items.map { item ->
                    UiSportMenuItem(
                        title = item.title,
                        isSelected = (data.sportMenu.selectedItem == item),
                        onClick = { data.sportMenu.selectItem(item) },
                    )
                }
                SportMenu(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .padding(end = 16.dp)
                        .focusProperties {
                            enter = { sportMenuFocusRequester }
                        },
                    items = uiSportMenuItems,
                    selectedItemFocusRequester = sportMenuFocusRequester,
                    contentPadding = PaddingValues(vertical = 48.dp),
                )
                BackHandler {
                    // move focus before removing element with focus from composition
                    mainMenuFocusRequester.requestFocus()
                    data.toggleSportMenu(open = false)
                }
            }
        }

        val focusInterceptorInteractionSource = remember { MutableInteractionSource() }
        val isFocusInterceptorFocused =
            focusInterceptorInteractionSource.collectIsFocusedAsState().value
        Box(
            modifier = Modifier
                .matchParentSize()
                .onFocusChanged {
                    if (it.isFocused) {
                        val isSelectedMainMenuItemASportsItem =
                            (data.mainMenu.selectedItem.type == MainMenuItem.Type.Sports)
                        if (isSelectedMainMenuItemASportsItem) {
                            data.toggleSportMenu(open = true)
                        }
                    }
                }
//                .run {
//                    if (isFocusInterceptorFocused) background(Color.Yellow)
//                    else this
//                }
                .focusable(interactionSource = focusInterceptorInteractionSource),
        )
    }

    LaunchedEffect(data.isMenuOpen) {
        if (data.isMenuOpen) {
            focusRequester().requestFocus()
        }
    }
    LaunchedEffect(data.isMenuOpen) {
        menuState.isMenuOpen.value = data.isMenuOpen
    }
    LaunchedEffect(data.sportMenu.isOpen) {
        if (data.isMenuOpen) {
            focusRequester().requestFocus()
        }
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
            menuState = remember {
                MutableMenuState(
                    isMenuOpen = false,
                    toggleMenuAction = {},
                )
            },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

@Suppress("SpellCheckingInspection")
private fun previewData() =
    MenuData(
        isMenuOpen = false,
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
            isOpen = false,
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
            selectItem = {},
        ),
        toggleMenu = {},
        toggleSportMenu = {},
    )