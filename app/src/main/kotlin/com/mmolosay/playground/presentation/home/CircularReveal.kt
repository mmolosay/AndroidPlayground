package com.mmolosay.playground.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
    startContent: @Composable BoxScope.() -> Unit,
    endContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    val endContentRevealFraction by animateFloatAsState(
        label = "end content reveal",
        targetValue = 1f,
        animationSpec = tween(durationMillis = 3000),
    )
    val isEndContentFullyRevealed = (endContentRevealFraction == 1f)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        if (!isEndContentFullyRevealed) {
            startContent()
        }
        Box(
            modifier = Modifier.circleClip(radiusFraction = endContentRevealFraction),
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
        startContent = startContent,
        endContent = endContent,
    )
}