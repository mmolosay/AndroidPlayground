package io.github.mmolosay.playground.presentation.tv.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

fun Modifier.edgeToEdge(
    horizontalOffset: Dp,
): Modifier =
    layout { measurable, constraints ->
        val totalOffset = horizontalOffset.toPx()
        val totalOffsetInt = totalOffset.roundToInt()
        val expandedConstraints = constraints.copy(
            maxWidth = constraints.maxWidth + totalOffsetInt,
        )
        val placeable = measurable.measure(expandedConstraints)
        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = 0, y = 0)
        }
    }