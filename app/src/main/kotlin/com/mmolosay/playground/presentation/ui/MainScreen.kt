package com.mmolosay.playground.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.presentation.design.PlaygroundTheme
import com.mmolosay.playground.presentation.ui.common.Screen

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
        var loading by remember { mutableStateOf(false) }
        LoadingButton(
            onClick = { loading = true },
            loading = loading,
        )
        Button(
            onClick = { loading = false },
            enabled = loading,
        ) {
            Text(text = "Stop")
        }
    }
}

// region Previews

@Preview(
    showBackground = true,
)
@Composable
private fun MainPreview() {
    PlaygroundTheme {
        MainScreen()
    }
}

// endregion
