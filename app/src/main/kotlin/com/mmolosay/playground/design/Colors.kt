package com.mmolosay.playground.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun getColorScheme(theme: Theme) =
    when (theme) {
        Theme.Light -> getColorScheme(useDark = false)
        Theme.Dark -> getColorScheme(useDark = true)
        Theme.DayNight -> getColorScheme(useDark = isSystemInDarkTheme())
    }

private fun getColorScheme(useDark: Boolean) =
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

