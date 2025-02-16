package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal fun Modifier.animateMenuShadow(
    isMenuOpen: Boolean,
): Modifier =
    composed {
        val openMenuShadowWidth = 16.dp
        val closedMenuShadowWidth = 0.dp
        val animatedMenuShadowWidth by animateDpAsState(
            targetValue = if (isMenuOpen) openMenuShadowWidth else closedMenuShadowWidth,
            label = "menu shadow width",
        )
        menuShadow(width = animatedMenuShadowWidth)
    }

internal fun Modifier.menuShadow(
    width: Dp,
): Modifier =
    this.run {
        if (width == Dp.Unspecified || width < Dp.VisibilityThreshold) {
            return@run this
        }
        this
            .graphicsLayer(clip = false)
            .composed {
                val density = LocalDensity.current
                val pxWidth = with(density) { width.toPx() }
                drawWithContent {
                    // TODO: make this work in RTL
                    val brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xCC_020304), Color.Transparent),
                        startX = this.size.width,
                        endX = this.size.width + pxWidth,
                    )
                    drawRect(
                        brush = brush,
                        topLeft = Offset(x = this.size.width, y = 0f),
                        size = Size(width = pxWidth, height = this.size.height),
                    )
                    drawContent()
                }
            }
    }