package com.mmolosay.playground.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Transition
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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
            val transition = updateTransition(
                targetState = loading,
                label = "master transition",
            )
            LoadingContent(
                transitionOfLoadingState = transition,
            )
            PrimaryContent(
                transitionOfLoadingState = transition,
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun LoadingContent(
    transitionOfLoadingState: Transition<Boolean>,
) {
    transitionOfLoadingState.AnimatedVisibility(
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
    transitionOfLoadingState: Transition<Boolean>,
) {
//    val density = LocalDensity.current
//    val softEdgeWidthPx = remember(density) { with(density) { 15.dp.toPx() } }
//    val alphaTransition = updateTransition(
//        targetState = visible,
//        label = "alphaTransition",
//    )
//    val softEdgeAlpha by alphaTransition.animateFloat(
//        transitionSpec = {
////            true isTransitioningTo false
//            spring(
//                stiffness = SpringStiffness,
//                visibilityThreshold = FadeOutVisibilityThreshold,
//            )
//        },
//        targetValueByState = { visible -> if (visible) 1f else 0f },
//        label = "softEdgeAlpha",
//    )
    transitionOfLoadingState.AnimatedVisibility(
        visible = { loading -> !loading },
        enter = fadeIn() + expandHorizontally(expandFrom = Alignment.CenterHorizontally),
        exit = fadeOut(
            animationSpec = spring(
                stiffness = SpringStiffness,
                visibilityThreshold = FadeOutVisibilityThreshold,
            ),
        ) + shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally),
//        modifier = Modifier
//            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
//            .drawWithCache {
//                @Suppress("NAME_SHADOWING")
//                val softEdgeAlpha = softEdgeAlpha
//                val gradientColorsLtr =
//                    listOf(Color.Transparent, Color.Black.copy(alpha = softEdgeAlpha))
//                val leftSoftEdgeBrush = Brush.horizontalGradient(
//                    colors = gradientColorsLtr,
//                    endX = softEdgeWidthPx,
//                )
//                val rightSoftEdgeBrush = Brush.horizontalGradient(
//                    colors = gradientColorsLtr.reversed(),
//                    endX = softEdgeWidthPx,
//                )
//                onDrawWithContent {
//                    drawContent()
//                    if (softEdgeAlpha != 1f) {
//                        drawRect(
//                            brush = leftSoftEdgeBrush,
//                            size = Size(width = softEdgeWidthPx, height = size.height),
////                                    alpha = softEdgeAlpha,
//                            blendMode = BlendMode.DstIn,
//                        )
////                                    clipRect {  } // TODO: try instead of endX in brush
//                        translate(left = size.width - softEdgeWidthPx) {
//                            drawRect(
//                                brush = rightSoftEdgeBrush,
//                                size = Size(
//                                    width = softEdgeWidthPx,
//                                    height = size.height
//                                ),
////                                        alpha = softEdgeAlpha,
//                                blendMode = BlendMode.DstIn,
//                            )
//                        }
//                    }
//                }
//            },
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
