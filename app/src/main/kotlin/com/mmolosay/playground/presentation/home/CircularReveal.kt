package com.mmolosay.playground.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.tooling.preview.Preview
import com.mmolosay.playground.presentation.home.CircularRevealAnimator.Companion.FullyExpandedValue
import kotlin.math.hypot

@Composable
fun CircularReveal(
    animator: CircularRevealAnimator,
    startContent: @Composable BoxScope.() -> Unit,
    endContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEndContentFullyRevealed =
        (animator.progressAnimatable.value == FullyExpandedValue)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (!isEndContentFullyRevealed) {
            startContent()
        }
        Box(
            modifier = Modifier
                .circleClip(radiusFraction = animator.progressAnimatable.value),
            content = endContent,
        )
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
    radiusFraction: Float,
): Modifier =
    drawWithCache {
        require(radiusFraction in 0f..1f)
        val path = Path()
        val diagonal = hypot(this.size.width, this.size.height)

        onDrawWithContent {
            path.rewind()
            val circleRect = Rect(
                center = this.center,
                radius = (diagonal / 2) * radiusFraction,
            )
            path.addOval(circleRect)

            clipPath(path) {
                this@onDrawWithContent.drawContent()
            }
        }
    }

@Preview
@Composable
private fun Preview() {
    val animator = remember { CircularRevealAnimator() }
    val startContent: @Composable BoxScope.() -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
        )
    }
    val endContent: @Composable BoxScope.() -> Unit = {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green),
        )
    }
    CircularReveal(
        animator = animator,
        startContent = startContent,
        endContent = endContent,
    )
    LaunchedEffect(Unit) {
        animator.expand()
    }
}