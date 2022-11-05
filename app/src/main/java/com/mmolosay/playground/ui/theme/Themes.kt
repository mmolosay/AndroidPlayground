package com.mmolosay.playground.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundTheme(
    theme: Theme = Theme.DayNight,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = getColorScheme(theme),
    ) {
        content()
    }
}

enum class Theme {
    Light,
    Dark,
    DayNight,
}