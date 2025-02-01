package com.mmolosay.playground.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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

@Composable
fun CircularReveal(
    progress: Float,
    startContent: @Composable () -> Unit,
    endContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    position: (Size) -> Offset = { it.center },
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        startContent()
        Box(
            modifier = Modifier
                .circleClip(
                    center = position,
                    radiusFraction = progress,
                ),
        ) {
            endContent()
        }
    }
}

class CircularRevealAnimator(
    val progressAnimatable: Animatable<Float, AnimationVector1D> =
        Animatable(initialValue = FullyCollapsedValue),
    private val defaultAnimationSpec: () -> AnimationSpec<Float> =
        { spring(stiffness = Spring.StiffnessLow) },
) {

    suspend fun expand() {
        progressAnimatable.animateTo(
            targetValue = FullyExpandedValue,
            animationSpec = defaultAnimationSpec(),
        )
    }

    suspend fun collapse() {
        progressAnimatable.animateTo(
            targetValue = FullyCollapsedValue,
            animationSpec = defaultAnimationSpec(),
        )
    }

    suspend fun play() {
        if (progressAnimatable.isRunning) return
        when (progressAnimatable.value) {
            FullyCollapsedValue -> expand()
            FullyExpandedValue -> collapse()
            else -> return // do nothing
        }
    }

    companion object {
        const val FullyCollapsedValue = 0f
        const val FullyExpandedValue = 1f
    }
}

private fun Modifier.circleClip(
    center: (Size) -> Offset,
    radiusFraction: Float,
): Modifier =
    drawWithCache {
        require(radiusFraction in 0f..1f)
        val path = Path()
        val center = center(this.size)
        val radiusOfCoveringCircle = center.radiusOfCoveringCircle(this.size.toRect())

        onDrawWithContent {
            path.rewind()
            val circleRect = Rect(
                center = center,
                radius = radiusOfCoveringCircle * radiusFraction,
            )
            path.addOval(circleRect)

            clipPath(path) {
                this@onDrawWithContent.drawContent()
            }
        }
    }

/**
 * Calculates a radius of a smallest circle that will fully cover given [rect].
 * The center of the circle is at receiver [Offset].
 * Assuming that [rect] is placed at [Offset.Zero].
 */
private fun Offset.radiusOfCoveringCircle(rect: Rect): Float {
    val center = this
    val corners = listOf(
        Offset(0f, 0f), // top left
        Offset(rect.width, 0f), // top right
        Offset(rect.width, rect.height), // bottom right
        Offset(0f, rect.height), // bottom left
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
    val startContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
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
    CircularReveal(
        progress = animator.progressAnimatable.value,
        startContent = startContent,
        endContent = endContent,
    )
    LaunchedEffect(Unit) {
        animator.expand()
    }
}