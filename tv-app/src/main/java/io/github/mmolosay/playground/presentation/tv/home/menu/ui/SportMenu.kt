package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.gestures.BringIntoViewSpec
import androidx.compose.foundation.gestures.LocalBringIntoViewSpec
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme

internal data class UiSportMenuItem(
    val title: String,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SportMenu(
    items: List<UiSportMenuItem>,
    selectedItemFocusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalBringIntoViewSpec provides PivotSpec(parentFraction = 0.5f, childFraction = 0.5f),
    ) {
        Column(
            modifier = modifier
                .verticalScroll(state = rememberScrollState())
                .focusGroup(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items.forEach { item ->
                Item(
                    item = item,
                    selectedItemFocusRequester = selectedItemFocusRequester,
                )
            }
        }
    }
}

@Composable
private fun Item(
    item: UiSportMenuItem,
    selectedItemFocusRequester: FocusRequester,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Item(
        modifier = Modifier
            .run {
                if (item.isSelected) focusRequester(selectedItemFocusRequester)
                else this
            }
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                role = Role.Tab,
                onClick = item.onClick,
            ),
        item = item,
        isFocused = interactionSource.collectIsFocusedAsState().value,
    )
}

@Composable
private fun Item(
    item: UiSportMenuItem,
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
                .fillMaxHeight(fraction = 0.60f),
        ) {
            if (isFocused) {
                ItemIconSelectionIndicator()
            }
        }

        Spacer(Modifier.width(6.dp))
        val color = when (item.isSelected) {
            true -> Chalk
            false -> Concrete
        }
        Text(
            text = item.title,
            color = color,
            maxLines = 1,
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

@OptIn(ExperimentalFoundationApi::class)
private class PivotSpec(
    private val parentFraction: Float = 0.5f,
    private val childFraction: Float = 0.5f
) : BringIntoViewSpec {

    override fun calculateScrollDistance(
        offset: Float, // initial position of item requesting focus
        size: Float, // size of item requesting focus
        containerSize: Float, // size of the lazy container
    ): Float {
        val childSmallerThanParent = size <= containerSize
        val initialTargetForLeadingEdge =
            parentFraction * containerSize - (childFraction * size)
        val spaceAvailableToShowItem = containerSize - initialTargetForLeadingEdge
        val targetForLeadingEdge =
            if (childSmallerThanParent && spaceAvailableToShowItem < size) {
                containerSize - size
            } else {
                initialTargetForLeadingEdge
            }
        return offset - targetForLeadingEdge
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
            items = previewItems(),
            selectedItemFocusRequester = remember { FocusRequester() },
        )
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}

private fun previewItems() =
    listOf(
        UiSportMenuItem(
            title = "NFL",
            isSelected = true,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "Boxing",
            isSelected = false,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "women-football",
            isSelected = false,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "Soccer",
            isSelected = false,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "MMA",
            isSelected = false,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "Something longer",
            isSelected = false,
            onClick = {},
        ),
        UiSportMenuItem(
            title = "Something even more longer",
            isSelected = false,
            onClick = {},
        ),
    )