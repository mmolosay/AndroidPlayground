package com.mmolosay.playground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun getColorScheme(theme: Theme) =
    when (theme) {
        Theme.Light -> lightColorScheme
        Theme.Dark -> darkColorScheme
        Theme.DayNight -> getColorScheme(useDark = isSystemInDarkTheme())
    }

private fun getColorScheme(useDark: Boolean) =
    when (useDark) {
        false -> lightColorScheme
        true -> darkColorScheme
    }

private val lightColorScheme =
    lightColorScheme()

private val darkColorScheme =
    darkColorScheme()

