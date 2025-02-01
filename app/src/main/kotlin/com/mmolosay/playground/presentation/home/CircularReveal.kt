package com.mmolosay.playground.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
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
import kotlin.math.hypot

@Composable
fun CircularReveal(
    animatable: Animatable<Float, AnimationVector1D>,
    startContent: @Composable BoxScope.() -> Unit,
    endContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEndContentFullyRevealed = (animatable.value == 1f)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (!isEndContentFullyRevealed) {
            startContent()
        }
        Box(
            modifier = Modifier.circleClip(radiusFraction = animatable.value),
            content = endContent,
        )
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
    val animatable = remember { Animatable(0f) }
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
        animatable = animatable,
        startContent = startContent,
        endContent = endContent,
    )
    LaunchedEffect(Unit) {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(3000),
        )
    }
}