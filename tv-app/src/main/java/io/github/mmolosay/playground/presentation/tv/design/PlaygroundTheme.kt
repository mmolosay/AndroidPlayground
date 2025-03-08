package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

/**
 * Analogue of [MaterialTheme] object.
 */
object PlaygroundTheme {

    val colorScheme: PlaygroundColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaygroundColorScheme.current
}