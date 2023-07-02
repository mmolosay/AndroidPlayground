package com.mmolosay.playground.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.design.PlaygroundTheme
import com.mmolosay.playground.ui.common.Screen
import com.mmolosay.playground.ui.common.ToggleLayout
import com.mmolosay.playground.ui.common.ToggleLayoutSize
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
        ToggleLoadingButton1()
        ToggleLoadingButton2()
    }
}

@Composable
fun ToggleLoadingButton1() {
    var showLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val onClick: () -> Unit = {
        scope.launch {
            showLoading = true // show loading when work is started
            delay(2.seconds)
            showLoading = false // hide loading once work is done
        }
    }
    ToggleLoadingButton(
        showLoading = showLoading,
        size = ToggleLayoutSize.MaxMeasurements,
        onClick = onClick,
    ) {
        Text("Click me")
    }
}

@Composable
fun ToggleLoadingButton2() {
    var showLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val onClick: () -> Unit = {
        scope.launch {
            showLoading = true // show loading when work is started
            delay(2.seconds)
            showLoading = false // hide loading once work is done
        }
    }
    ToggleLoadingButton(
        showLoading = showLoading,
        size = ToggleLayoutSize.SizeOfFirst,
        onClick = onClick,
    ) {
        Text("Click me")
    }
}

@Composable
fun ToggleLoadingButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showLoading: Boolean,
    size: ToggleLayoutSize,
    loading: @Composable () -> Unit = { DefaultLoading() },
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        ToggleLayout(
            modifier = modifier,
            size = size,
            showFirst = !showLoading,
            content1 = content,
            content2 = loading,
        )
    }
}

@Composable
fun DefaultLoading(
    modifier: Modifier = Modifier,
) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1.0f),
        color = Color.White,
        strokeWidth = 2.dp,
    )
}