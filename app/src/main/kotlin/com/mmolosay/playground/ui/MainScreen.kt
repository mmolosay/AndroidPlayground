package com.mmolosay.playground.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.design.PlaygroundTheme
import com.mmolosay.playground.ui.common.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

// region Previews

@Preview(
//    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
private fun MainPreview() {
    PlaygroundTheme {
        MainScreen()
    }
}

// endregion

@Composable
fun MainScreen() {
    Screen {
        Main()
    }
}

@Composable
fun Main() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var showLoading by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        val onClick: () -> Unit = {
            scope.launch {
                showLoading = true
                delay(2.seconds)
                showLoading = false // hide loading once work is done
            }
        }
        ToggleLoadingButton(
            showLoading = showLoading,
            onClick = onClick,
        ) {
            Text("Click me")
        }
    }
}

@Composable
fun ToggleLoadingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showLoading: Boolean,
    loading: @Composable () -> Unit = @Composable { DefaultLoading() },
    content: @Composable () -> Unit,
) {
    ToggleButton(
        modifier = modifier,
        showFirst = !showLoading,
        content1 = content,
        content2 = loading,
        onClick = onClick,
    )
}

@Composable
fun DefaultLoading() {
    CircularProgressIndicator(
        modifier = Modifier.size(24.dp),
        color = Color.White,
        strokeWidth = 2.dp,
    )
}

/**
 * Component that displays either [content1] or [content2] inside a [Button] depending on
 * the state of [showFirst].
 *
 * Resulting [Button] wrapper's width and height will be equal to max measurements of both contents,
 * so that toggling between them doesn't resize the button.
 */
@Composable
fun ToggleButton(
    modifier: Modifier,
    showFirst: Boolean,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
    ) {
        Layout(
            measurePolicy = { measurables, constraints ->
                val placeables = measurables.map { it.measure(constraints) }
                val width = placeables.maxOf { it.width }
                val height = placeables.maxOf { it.height }
                layout(width, height) {
                    val content = if (showFirst) placeables[0] else placeables[1]
                    val x = (width / 2) - (content.width / 2)
                    val y = (height / 2) - (content.height / 2)
                    content.placeRelative(x, y)
                }
            },
            content = {
                content1()
                content2()
            },
        )
    }
}