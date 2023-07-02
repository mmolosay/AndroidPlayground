package com.mmolosay.playground.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A variation of [ToggleLayout] which second content is loading component.
 *
 * This function is just a convenience for working with a [ToggleLayout].
 * All it does is just provide better names, like [loading] instead of [ToggleLayout]'s `showFirst`.
 */
@Composable
fun ToggleLoadingLayout(
    modifier: Modifier = Modifier,
    size: ToggleLayoutSize = ToggleLayoutSize.SizeOfFirst,
    loading: Boolean,
    loadingContent: @Composable () -> Unit,
    primaryContent: @Composable () -> Unit,
) =
    ToggleLayout(
        modifier = modifier,
        size = size,
        showFirst = !loading,
        content1 = primaryContent,
        content2 = loadingContent,
    )