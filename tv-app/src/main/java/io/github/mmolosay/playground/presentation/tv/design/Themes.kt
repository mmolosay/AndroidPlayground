package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaygroundTheme(
    theme: Theme = Theme.Dark,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = getColorScheme(theme),
    ) {
        CompositionLocalProvider(
            LocalContentColor provides MaterialTheme.colorScheme.onBackground,
            LocalRippleConfiguration provides RippleConfiguration,
        ) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private val RippleConfiguration =
    RippleConfiguration(
        color = Color.Unspecified,
        rippleAlpha = RippleAlpha(
            pressedAlpha = 0.10f,
            focusedAlpha = 0f,
            draggedAlpha = 0f,
            hoveredAlpha = 0.04f,
        ),
    )

enum class Theme {
    Light,
    Dark,
    DayNight,
}