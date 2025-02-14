package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.home.menu.MainMenuItem
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuState

// TODO: focus currently selected item when MainMenu is composed

@Composable
internal fun MainMenu(
    data: MenuData.MainMenu,
    menuState: MenuState,
    useAfterimageAppearance: Boolean,
    onFocusChanged: (FocusState) -> Unit,
    modifier: Modifier = Modifier,
) {
    var hasFocus by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp) // TODO: move to caller?
            .onFocusChanged {
                hasFocus = it.hasFocus
                onFocusChanged(it)
            }
            .focusGroup(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        data.items.forEach { item ->
            Item(
                item = item,
                onClick = { data.selectItem(item) },
                isSelected = (item == data.selectedItem),
                isSelfOrSiblingItemFocused = hasFocus,
                useAfterimageAppearance = useAfterimageAppearance,
                useCollapsedAppearance = !menuState.isMenuOpen,
            )
        }
    }
}

@Composable
private fun Item(
    item: MainMenuItem,
    onClick: () -> Unit,
    isSelected: Boolean,
    isSelfOrSiblingItemFocused: Boolean,
    useAfterimageAppearance: Boolean,
    useCollapsedAppearance: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Item(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Tab,
                onClick = onClick,
            ),
        item = item,
        isSelected = isSelected,
        isFocused = interactionSource.collectIsFocusedAsState().value,
        isSelfOrSiblingItemFocused = isSelfOrSiblingItemFocused,
        useAfterimageAppearance = useAfterimageAppearance,
        useCollapsedAppearance = useCollapsedAppearance,
    )
}

@Composable
private fun Item(
    item: MainMenuItem,
    isSelected: Boolean,
    isFocused: Boolean,
    isSelfOrSiblingItemFocused: Boolean,
    useAfterimageAppearance: Boolean,
    useCollapsedAppearance: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val contentColor = LocalContentColor.current
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min), // ItemIconSelectionIndicator()'s Box has fillMaxWidth()
            horizontalAlignment = Alignment.CenterHorizontally,
        ) IconSection@{
            val iconTint = when {
                useAfterimageAppearance -> contentColor.copy(alpha = 0.30f)
                isSelected -> contentColor
                else -> contentColor.copy(alpha = 0.80f)
            }
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = item.toImageVector(),
                contentDescription = null, // there's accompanying Text besides
                tint = iconTint,
            )
            Column(
                modifier = Modifier
                    .width(12.dp)
                    .height(4.dp), // 2dp Spacer + 2dp indicator height
            ) {
                val showIndicator = if (isSelfOrSiblingItemFocused) {
                    isFocused && !useAfterimageAppearance
                } else {
                    isSelected && !useAfterimageAppearance
                }
                if (showIndicator) {
                    Spacer(Modifier.height(2.dp))
                    ItemIconSelectionIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                    )
                }
            }
        }

        if (!useCollapsedAppearance && !useAfterimageAppearance) {
            Spacer(Modifier.width(12.dp))
            val color = when (isFocused) {
                true -> contentColor
                false -> contentColor.copy(alpha = 0.8f)
            }
            Text(
                text = item.title,
                color = color,
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Composable
private fun ItemIconSelectionIndicator(
    modifier: Modifier,
) {
    val contentColor = LocalContentColor.current
    Canvas(modifier) {
        drawRect(
            color = contentColor,
            topLeft = Offset.Zero,
            size = this.size,
            style = Fill,
        )
    }
}

private fun MainMenuItem.toImageVector(): ImageVector =
    when (this.type) {
        MainMenuItem.Type.Home -> Icons.Default.Home
        MainMenuItem.Type.Sports -> Icons.AutoMirrored.Default.List
        MainMenuItem.Type.Schedule -> Icons.Default.DateRange
        MainMenuItem.Type.LiveTv -> Icons.Default.Star
        MainMenuItem.Type.Settings -> Icons.Default.Settings
    }

@Preview
@Composable
private fun MainMenu_Closed_Preview() {
    PlaygroundTheme {
        MainMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            data = previewData(),
            menuState = MenuState(
                isMenuOpen = false,
                isSportMenuOpen = false,
            ),
            useAfterimageAppearance = false,
            onFocusChanged = {},
        )
    }
}

@Preview
@Composable
private fun MainMenu_Closed_Afterimage_Preview() {
    PlaygroundTheme {
        MainMenu(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            data = previewData(),
            menuState = MenuState(
                isMenuOpen = false,
                isSportMenuOpen = false,
            ),
            useAfterimageAppearance = true,
            onFocusChanged = {},
        )
    }
}

@Preview
@Composable
private fun MainMenu_Open_Preview() {
    PlaygroundTheme {
        val focusRequester = remember { FocusRequester() }
        MainMenu(
            modifier = Modifier
                .focusRequester(focusRequester)
                .background(MaterialTheme.colorScheme.background),
            data = previewData(),
            menuState = MenuState(
                isMenuOpen = true,
                isSportMenuOpen = false,
            ),
            useAfterimageAppearance = false,
            onFocusChanged = {},
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

private fun previewData() =
    MenuData.MainMenu(
        selectedItem = MainMenuItem(
            type = MainMenuItem.Type.Schedule,
            title = "Schedule",
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
    )