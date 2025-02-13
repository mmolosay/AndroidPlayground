package com.mmolosay.playground.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.hypot

class CircularRevealAnimator(
    val progressAnimatable: Animatable<Float, AnimationVector1D> =
        Animatable(initialValue = FullyCollapsedValue),
    private val animationSpec: AnimationSpec<Float> =
        spring(stiffness = 100f),
) {

    suspend fun expand() {
        progressAnimatable.animateTo(
            targetValue = FullyExpandedValue,
            animationSpec = animationSpec,
        )
    }

    suspend fun collapse() {
        progressAnimatable.animateTo(
            targetValue = FullyCollapsedValue,
            animationSpec = animationSpec,
        )
    }

    companion object {
        const val FullyCollapsedValue = 0f
        const val FullyExpandedValue = 1f
    }
}

fun Modifier.clipCircle(
    center: (Size) -> Offset,
    coveringRadiusFraction: Float,
): Modifier =
    drawWithCache {
        require(coveringRadiusFraction in 0f..1f)
        val path = Path()
        val center = center(this.size)
        val radiusOfCoveringCircle = center.radiusOfCoveringCircle(this.size.toRect())

        onDrawWithContent {
            path.rewind()
            val circleRect = Rect(
                center = center,
                radius = radiusOfCoveringCircle * coveringRadiusFraction,
            )
            path.addOval(circleRect)

            clipPath(path) {
                this@onDrawWithContent.drawContent()
            }
        }
    }

/**
 * Calculates a radius of a smallest circle that will fully cover given [rect].
 * The center of the circle is at receiver [Offset], and [rect] is placed at [Offset.Zero].
 */
private fun Offset.radiusOfCoveringCircle(rect: Rect): Float {
    val center = this
    val corners = listOf(
        rect.topLeft,
        rect.topRight,
        rect.bottomRight,
        rect.bottomLeft,
    )
    val distanceToCorners = corners.map { corner ->
        hypot(corner.x - center.x, corner.y - center.y)
    }
    val distanceToFurthestCorner = distanceToCorners.max()
    return distanceToFurthestCorner
}

@Preview
@Composable
private fun Preview() {
    val animator = remember { CircularRevealAnimator() }
    val startContent: @Composable (Modifier) -> Unit = { modifier ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Blue),
        )
    }
    val endContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
        )
    }
    Box {
        endContent()
        startContent(
            Modifier.clipCircle(
                center = { size -> size.center },
                coveringRadiusFraction = animator.progressAnimatable.value,
            )
        )
    }
    LaunchedEffect(Unit) {
        animator.expand()
    }
}