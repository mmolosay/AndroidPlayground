package com.mmolosay.playground.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.presentation.design.PlaygroundTheme

@Composable
fun LoadingButton(
    onClick: () -> Unit,
    loading: Boolean,
) {
    Button(
        onClick = onClick,
    ) {
        Box(contentAlignment = Alignment.Center) {
            PrimaryContent(visible = !loading)
            LoadingContent(visible = loading)
        }
    }
}

@Composable
private fun LoadingContent(
    visible: Boolean,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            ),
        ),
        label = "Button's CircularProgressIndicator AnimatedVisibility",
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = LocalContentColor.current,
            strokeWidth = 1.5f.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

@Composable
private fun PrimaryContent(
    visible: Boolean,
) {
    val density = LocalDensity.current
    val softEdgeWidthPx = remember(density) { with(density) { 15.dp.toPx() } }
    val alphaTransition = updateTransition(
        targetState = visible,
        label = "alphaTransition",
    )
    val softEdgeAlpha by alphaTransition.animateFloat(
        transitionSpec = {
//            true isTransitioningTo false
            spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            )
        },
        targetValueByState = { visible -> if (visible) 1f else 0f },
        label = "softEdgeAlpha",
    )
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandHorizontally(expandFrom = Alignment.CenterHorizontally),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            ),
        ) + shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally),
        label = "Button's Text AnimatedVisibility",
        modifier = Modifier
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithCache {
                @Suppress("NAME_SHADOWING")
                val softEdgeAlpha = softEdgeAlpha
                val gradientColorsLtr =
                    listOf(Color.Transparent, Color.Black.copy(alpha = softEdgeAlpha))
                val leftSoftEdgeBrush = Brush.horizontalGradient(
                    colors = gradientColorsLtr,
                    endX = softEdgeWidthPx,
                )
                val rightSoftEdgeBrush = Brush.horizontalGradient(
                    colors = gradientColorsLtr.reversed(),
                    endX = softEdgeWidthPx,
                )
                onDrawWithContent {
                    drawContent()
                    if (softEdgeAlpha != 1f) {
                        drawRect(
                            brush = leftSoftEdgeBrush,
                            size = Size(width = softEdgeWidthPx, height = size.height),
//                                    alpha = softEdgeAlpha,
                            blendMode = BlendMode.DstIn,
                        )
//                                    clipRect {  } // TODO: try instead of endX in brush
                        translate(left = size.width - softEdgeWidthPx) {
                            drawRect(
                                brush = rightSoftEdgeBrush,
                                size = Size(
                                    width = softEdgeWidthPx,
                                    height = size.height
                                ),
//                                        alpha = softEdgeAlpha,
                                blendMode = BlendMode.DstIn,
                            )
                        }
                    }
                }
            },
    ) {
        Text(
//                        text = "Place the order",
//                        text = "龗龗龗 龗龗龗 龗龗龗",
//            text = "##############",
            text = "ABCDEFGHIJKOPQRSTUVWXYZ",
        )
    }
}

private val SpringStiffness = Spring.StiffnessMediumLow
private val FadeOutVisibilityThreshold = 0.20f

@Preview
@Composable
private fun LoadingButtonPreview() {
    PlaygroundTheme {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            LoadingButton(
                onClick = {},
                loading = false,
            )
        }
    }
}
