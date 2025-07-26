package io.github.mmolosay.playground.presentation.tv.screen

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.common.FocusableElement
import io.github.mmolosay.playground.presentation.tv.menu.ui.ContentFocusRequester
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RailsScreen(
    contentFocusRequester: ContentFocusRequester,
    stubFocusRequester: FocusRequester,
    startPadding: Dp,
) {
    Row(
        modifier = Modifier
            .padding(start = startPadding),
    ) {
        FocusableElement(
            modifier = Modifier
                .width(64.dp)
                .fillMaxHeight()
                .focusRequester(stubFocusRequester),
        )

        Spacer(Modifier.width(16.dp))
        val railsFocusRequester = remember { FocusRequester() }
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .focusRequester(railsFocusRequester)
                .focusProperties {
                    exit = {
                        val hasSaved = railsFocusRequester.saveFocusedChild()
                        FocusRequester.Default
                    }
                    enter = {
                        val hasRestored = railsFocusRequester.restoreFocusedChild()
                        if (hasRestored) FocusRequester.Cancel else FocusRequester.Default
                    }
                }
                .focusGroup(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            repeat(times = 4) { railIndex ->
                key(railIndex) {
                    RailOfTiles(
                        modifier = Modifier,
                        title = "Rail $railIndex",
                        railIndex = railIndex,
                    )
                }
            }
        }

        LaunchedEffect(contentFocusRequester) {
            contentFocusRequester.eventFlow.collectLatest { event ->
                delay(3200.milliseconds)
                val hasRestored = railsFocusRequester.restoreFocusedChild()
                if (!hasRestored) {
                    railsFocusRequester.requestFocus()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun RailOfTiles(
    modifier: Modifier,
    title: String,
    railIndex: Int,
) {
    Column(
        modifier = modifier
            .focusGroup(),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .horizontalScroll(state = rememberScrollState())
                .focusGroup(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            repeat(times = 10) { tileIndex ->
                key(tileIndex) {
                    Tile(
                        text = "$railIndex.$tileIndex",
                    )
                }
            }
        }
    }
}

@Composable
private fun Tile(
    text: String,
) {
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
            text = text,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}