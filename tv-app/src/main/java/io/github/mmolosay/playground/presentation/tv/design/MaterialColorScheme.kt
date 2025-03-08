package io.github.mmolosay.playground.presentation.tv.design

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.ColorScheme as MaterialColorScheme

@Composable
fun getMaterialColorScheme(theme: Theme): MaterialColorScheme =
    when (theme) {
        Theme.Light -> getMaterialColorScheme(useDark = false)
        Theme.Dark -> getMaterialColorScheme(useDark = true)
        Theme.DayNight -> getMaterialColorScheme(useDark = isSystemInDarkTheme())
    }

private fun getMaterialColorScheme(useDark: Boolean): MaterialColorScheme =
    when (useDark) {
        false -> lightMaterialColorScheme
        true -> darkMaterialColorScheme
    }

private val lightMaterialColorScheme by lazy {
    lightColorScheme()
}

private val darkMaterialColorScheme by lazy {
    darkColorScheme()
}

