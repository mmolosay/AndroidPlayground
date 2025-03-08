package io.github.mmolosay.playground.presentation.tv.common

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme

object ButtonDefaultsUtil {

    @Composable
    fun focusAwareContainerColor(interactionSource: InteractionSource): Color {
        val isFocused = interactionSource.collectIsFocusedAsState().value
        return when (isFocused) {
            true -> PlaygroundTheme.colorScheme.focus
            false -> Color.Unspecified // ButtonDefaults.buttonColors() will use default value
        }
    }
}