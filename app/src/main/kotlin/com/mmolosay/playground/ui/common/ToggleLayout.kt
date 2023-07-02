package com.mmolosay.playground.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

/**
 * Layout that displays either [content1] or [content2] depending on value of [showFirst] flag.
 * The size of this layout is determined by the [size].
 */
@Composable
fun ToggleLayout(
    modifier: Modifier,
    size: ToggleLayoutSize,
    showFirst: Boolean,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
) {
    when (size) {
        ToggleLayoutSize.MaxMeasurements ->
            ToggleLayoutMaxMeasurements(
                modifier = modifier,
                showFirst = showFirst,
                content1 = content1,
                content2 = content2,
            )

        ToggleLayoutSize.SizeOfFirst ->
            ToggleLayoutSizeOfFirst(
                modifier = modifier,
                showFirst = showFirst,
                content1 = content1,
                content2 = content2,
            )
    }
}

enum class ToggleLayoutSize {
    MaxMeasurements,
    SizeOfFirst,
}

/**
 * Implementation of [ToggleLayout] of [ToggleLayoutSize.MaxMeasurements] type.
 *
 * Width and height of this layout will be equal to max measurements of both contents,
 * so that toggling between them doesn't cause a resize.
 **/
@Composable
fun ToggleLayoutMaxMeasurements(
    modifier: Modifier,
    showFirst: Boolean,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map { it.measure(constraints) }
            val width = placeables.maxOf { it.width }
            val height = placeables.maxOf { it.height }
            layout(width, height) {
                val content = if (showFirst) placeables[0] else placeables[1]
                val x = (width / 2) - (content.width / 2)
                val y = (height / 2) - (content.height / 2)
                content.placeRelative(x, y)
            }
        },
        content = {
            content1()
            content2()
        },
    )
}


/**
 * Implementation of [ToggleLayout] of [ToggleLayoutSize.SizeOfFirst] type.
 *
 * Size of this layout will be equal to size of [content1]. Size of [content2] will be coerced
 * in this range.
 **/
@Composable
fun ToggleLayoutSizeOfFirst(
    modifier: Modifier,
    showFirst: Boolean,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeable1 = measurables[0].measure(constraints)
            val width = placeable1.width
            val height = placeable1.height
            val coerceConstraints = constraints.copy(
                maxWidth = width,
                maxHeight = height,
            )
            val placeable2 = measurables[1].measure(coerceConstraints)
            layout(width, height) {
                val content = if (showFirst) placeable1 else placeable2
                val x = (width / 2) - (content.width / 2)
                val y = (height / 2) - (content.height / 2)
                content.placeRelative(x, y)
            }
        },
        content = {
            content1()
            content2()
        },
    )
}