package io.github.mmolosay.playground.presentation.tv.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme

@Composable
fun FocusableElement(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: (@Composable BoxScope.() -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val backgroundColor: Color
    val contentColor: Color
    if (isFocused) {
        backgroundColor = PlaygroundTheme.colorScheme.focus
        contentColor = PlaygroundTheme.colorScheme.onFocus
    } else {
        backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    }
    Box(
        modifier = modifier
            .run {
                if (content == null) size(width = 180.dp, height = 100.dp)
                else this
            }
            .background(backgroundColor)
            .run {
                if (content != null) padding(horizontal = 24.dp, vertical = 16.dp)
                else this
            }
            .run {
                if (onClick != null) {
                    clickable(
                        onClick = onClick,
                        indication = null,
                        interactionSource = interactionSource,
                    )
                } else {
                    focusable(interactionSource = interactionSource)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
        ) {
            content?.invoke(this)
        }
    }
}