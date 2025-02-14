package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.home.menu.SportMenuItem

@Composable
internal fun SportMenu(
    data: MenuData.SportMenu,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp) // TODO: move to caller?
            .focusGroup(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        data.items
            .take(4) // TODO:
            .forEach { item ->
                Item(
                    item = item,
                    isSelected = (item == data.selectedItem),
                )
            }
    }
}

@Composable
private fun Item(
    item: SportMenuItem,
    isSelected: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Item(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Tab,
                onClick = { /* TODO: implement me */ },
            ),
        item = item,
        isSelected = isSelected,
        isFocused = interactionSource.collectIsFocusedAsState().value,
    )
}

@Composable
private fun Item(
    item: SportMenuItem,
    isSelected: Boolean,
    isFocused: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min), // ItemIconSelectionIndicator()'s Box has fillMaxHeight()
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight(),
        ) {
            if (isFocused) {
                ItemIconSelectionIndicator()
            }
        }

        Spacer(Modifier.width(12.dp))
        val contentColor = LocalContentColor.current
        val color = when (isSelected) {
            true -> contentColor
            false -> contentColor.copy(alpha = 0.80f)
        }
        Text(
            text = item.title,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun ItemIconSelectionIndicator() {
    val contentColor = LocalContentColor.current
    Canvas(
        modifier = Modifier.fillMaxSize(),
    ) {
        drawRect(
            color = contentColor,
            topLeft = Offset.Zero,
            size = this.size,
            style = Fill,
        )
    }
}

@Preview
@Composable
private fun SportMenu_Preview() {
    PlaygroundTheme {
        val focusRequester = remember { FocusRequester() }
        SportMenu(
            modifier = Modifier
                .focusRequester(focusRequester)
                .background(Color.Black),
            data = previewData(),
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

private fun previewData() =
    MenuData.SportMenu(
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
                id = "Women's Football",
                title = "women-football",
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
        )
    )