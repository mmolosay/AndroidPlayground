package com.mmolosay.playground.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.VisibilityThreshold
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
import androidx.compose.ui.geometry.Offset
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
import androidx.compose.ui.unit.IntSize
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
            val transition = updateTransition(
                targetState = loading,
                label = "master transition",
            )
            LoadingContent(
                loadingStateTransition = transition,
            )
            PrimaryContent(
                loadingStateTransition = transition,
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun LoadingContent(
    loadingStateTransition: Transition<Boolean>,
) {
    loadingStateTransition.AnimatedVisibility(
        visible = { loading -> loading },
        enter = fadeIn(),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            ),
        ),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(18.dp),
            color = LocalContentColor.current,
            strokeWidth = 1.5f.dp,
            strokeCap = StrokeCap.Round,
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PrimaryContent(
    loadingStateTransition: Transition<Boolean>,
) {
    val density = LocalDensity.current
    val softEdgeWidthPx = remember(density) { with(density) { 20.dp.toPx() } }
    val softEdgeProgress by loadingStateTransition.animateFloat(
        transitionSpec = {
            spring(
                stiffness = SpringStiffness,
            )
        },
        targetValueByState = { loading -> if (loading) 1f else 0f },
        label = "soft edge progress",
    )
    loadingStateTransition.AnimatedVisibility(
        visible = { loading -> !loading },
        enter = fadeIn() + expandHorizontally(
            animationSpec = spring(
                stiffness = SpringStiffness,
                dampingRatio = WidthAnimationSpringDumpingRatio,
                visibilityThreshold = IntSize.VisibilityThreshold,
            ),
            expandFrom = Alignment.CenterHorizontally,
        ),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            ),
        ) + shrinkHorizontally(
            animationSpec = spring(
                stiffness = SpringStiffness,
                // dampingRatio is not applicable here, size cannot become negative
                visibilityThreshold = IntSize.VisibilityThreshold,
            ),
            shrinkTowards = Alignment.CenterHorizontally,
        ),
        modifier = Modifier
            .horizontalSoftEdge(
                progress = { softEdgeProgress },
                edgeWidthPx = softEdgeWidthPx,
            ),
    ) {
        Text(
            text = "Place the order",
        )
    }
}

private fun Modifier.horizontalSoftEdge(
    progress: () -> Float,
    edgeWidthPx: Number,
): Modifier =
    this
        .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        .drawWithCache {
            val softEdgeProgress = progress()
            val edgeWidth = edgeWidthPx.toFloat()

            val gradientColorsLtr = listOf(Color.Transparent, Color.Black)
            val gradientColorsRtl = gradientColorsLtr.reversed()
            fun directionalHorizontalGradient(ltr: Boolean) =
                Brush.horizontalGradient(
                    colors = if (ltr) gradientColorsLtr else gradientColorsRtl,
                    endX = edgeWidth,
                )

            val leftSoftEdgeBrush = directionalHorizontalGradient(ltr = true)
            val rightSoftEdgeBrush = directionalHorizontalGradient(ltr = false)
            val softEdgeSize = Size(width = edgeWidth, height = size.height)
            val blendMode = BlendMode.DstIn

            onDrawWithContent {
                drawContent()
                if (size.width == 0f) return@onDrawWithContent
                val x = if (softEdgeProgress <= 0.30f) {
                    val sectionProgress = softEdgeProgress / 0.30f
                    -edgeWidth + (edgeWidth * sectionProgress)
                } else {
                    val halfWidth = size.width / 2
                    if (halfWidth >= edgeWidth) {
                        0f
                    } else {
                        halfWidth - edgeWidth
                    }
                }
                translate(left = x) {
                    drawRect(
                        brush = leftSoftEdgeBrush,
                        size = softEdgeSize,
                        blendMode = blendMode,
                    )
                }
                translate(left = size.width - edgeWidth) {
                    drawRect(
                        brush = rightSoftEdgeBrush,
                        topLeft = Offset.Zero.copy(x = -x),
                        size = softEdgeSize,
                        blendMode = blendMode,
                    )
                }
            }
        }

private val SpringStiffness = Spring.StiffnessMediumLow
private val FadeOutVisibilityThreshold = 0.10f
private val WidthAnimationSpringDumpingRatio = Spring.DampingRatioLowBouncy

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
