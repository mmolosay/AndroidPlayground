package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.ColorScheme as MaterialColorScheme

@Composable
fun getColorScheme(theme: Theme): MaterialColorScheme =
    when (theme) {
        Theme.Light -> getColorScheme(useDark = false)
        Theme.Dark -> getColorScheme(useDark = true)
        Theme.DayNight -> getColorScheme(useDark = isSystemInDarkTheme())
    }

private fun getColorScheme(useDark: Boolean): MaterialColorScheme =
    when (useDark) {
        false -> lightColorScheme
        true -> darkColorScheme
    }

private val lightColorScheme by lazy {
    lightColorScheme()
}

private val darkColorScheme by lazy {
    darkColorScheme()
}

