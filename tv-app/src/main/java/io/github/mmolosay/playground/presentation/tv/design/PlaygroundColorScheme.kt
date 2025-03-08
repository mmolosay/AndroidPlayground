package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalPlaygroundColorScheme = staticCompositionLocalOf<PlaygroundColorScheme> {
    error("CompositionLocal \'LocalPlaygroundColorScheme\' doesn't have value provided by default")
}

data class PlaygroundColorScheme(
    val focus: Color,
    val onFocus: Color,
)

@Composable
fun getPlaygroundColorScheme(theme: Theme): PlaygroundColorScheme =
    when (theme) {
        Theme.Light -> getPlaygroundColorScheme(useDark = false)
        Theme.Dark -> getPlaygroundColorScheme(useDark = true)
        Theme.DayNight -> getPlaygroundColorScheme(useDark = isSystemInDarkTheme())
    }

private fun getPlaygroundColorScheme(useDark: Boolean): PlaygroundColorScheme =
    when (useDark) {
        false -> lightPlaygroundColorScheme
        true -> darkPlaygroundColorScheme
    }

private val lightPlaygroundColorScheme by lazy<PlaygroundColorScheme> {
    TODO()
}

private val darkPlaygroundColorScheme by lazy {
    PlaygroundColorScheme(
        focus = Color.Yellow,
        onFocus = Color(red = 20, green = 18, blue = 24), // ColorDarkTokens.Background from dark Material ColorScheme (PaletteTokens.Neutral6)
    )
}