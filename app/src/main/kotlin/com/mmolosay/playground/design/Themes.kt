package com.mmolosay.playground.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun PlaygroundTheme(
    theme: Theme = Theme.DayNight,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = getColorScheme(theme),
        content = content,
    )
}

enum class Theme {
    Light,
    Dark,
    DayNight,
}