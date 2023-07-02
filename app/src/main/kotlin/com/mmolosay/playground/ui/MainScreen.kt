package com.mmolosay.playground.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.design.PlaygroundTheme
import com.mmolosay.playground.ui.common.Screen
import io.github.mmolosay.debounce.debounced
import kotlinx.coroutines.CoroutineScope
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
        StyledToggleLoadingButton1()
        StyledToggleLoadingButton2()
    }
}

@Composable
fun StyledToggleLoadingButton1() {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    val onClick = debounced(1.seconds) {
        loading = true
        doWork(scope) { loading = false }
    }

    val loadingContent =
        @Composable {
            CircularProgressIndicator(
                color = LocalContentColor.current.copy(alpha = 0.5f),
                strokeWidth = 2.dp,
            )
        }

    StyledButton(
        onClick = onClick,
        enabled = !loading,
    ) {
        ToggleLoadingLayout(
            size = ToggleLayoutSize.MaxMeasurements,
            loading = loading,
            loadingContent = loadingContent,
        ) {
            Text("Click me")
        }
    }
}

@Composable
fun StyledToggleLoadingButton2() {
    val scope = rememberCoroutineScope()
    var loading by remember { mutableStateOf(false) }
    val onClick = debounced(1.seconds) {
        loading = true
        doWork(scope) { loading = false }
    }

    @Composable
    fun LoadingContent() =
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(1.0f),
            color = LocalContentColor.current.copy(alpha = 0.5f),
            strokeWidth = 1.5.dp,
        )

    StyledButton(
        onClick = onClick,
        enabled = !loading,
    ) {
        ToggleLoadingLayout(
            size = ToggleLayoutSize.SizeOfFirst,
            loading = loading,
            loadingContent = { LoadingContent() },
        ) {
            Text("Click me")
        }
    }
}

private fun doWork(
    coroutineScope: CoroutineScope,
    onDone: () -> Unit,
) {
    coroutineScope.launch {
        delay(2.seconds)
        onDone()
    }
}