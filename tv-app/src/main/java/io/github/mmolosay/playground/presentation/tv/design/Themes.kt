package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.runtime.Composable
import androidx.tv.material3.MaterialTheme

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