package com.mmolosay.playground.ui.components

import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.ui.theme.PlaygroundTheme

// region Previews

@Preview
@Composable
private fun RipplePreview() {
    PlaygroundTheme {
        Ripple(
            modifier = Modifier.background(Color(0xFF181818)),
            contentPadding = PaddingValues(80.dp, 40.dp)
        ) {
            Content()
        }
    }
}

// endregion

@Composable
fun Ripple(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    content: @Composable () -> Unit
) {
    RippleLayout(
        onClick = {},
        modifier = modifier,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
private fun RippleLayout(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    contentShape: Shape = RectangleShape,
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val indication = rememberRipple()
    val wrapped = @Composable {
        WrappedContent(interactionSource, indication, contentShape, content)
    }
    Layout(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick,
        ),
        content = wrapped,
        measurePolicy = rippleMeasurePolicy(contentPadding)
    )
}

@Composable
private fun WrappedContent(
    interactionSource: InteractionSource,
    indication: Indication,
    contentShape: Shape,
    content: @Composable () -> Unit,
) =
    Box(
        modifier = Modifier
            .clip(contentShape)
            .indication(interactionSource, indication)
    ) {
        content()
    }

@Composable
private fun rippleMeasurePolicy(
    contentPadding: PaddingValues,
): MeasurePolicy {
    val horizontal = with (LocalDensity.current) {
        contentPadding.calculateHorizontalPadding().roundToPx()
    }
    val vertical = with (LocalDensity.current) {
        contentPadding.calculateVerticalPadding().roundToPx()
    }
    return MeasurePolicy { measurables, constraints ->
        require(measurables.size == 1)
        val placeable = measurables.first().measure(constraints)

        layout(
            placeable.width + horizontal,
            placeable.height + vertical,
        ) {
            placeable.placeRelative(x = 0, y = 0)
        }
    }
}

@Composable
private fun Content() =
    Box(
        modifier = Modifier
            .border(1.dp, Color.White)
            .size(200.dp)
            .padding(20.dp)
            .border(1.dp, Color.Yellow)
            .background(Color(0xFF36612F))
    )

private fun PaddingValues.calculateHorizontalPadding(): Dp {
    val ld = LayoutDirection.Ltr
    return calculateLeftPadding(ld) + calculateRightPadding(ld)
}

private fun PaddingValues.calculateVerticalPadding(): Dp =
    calculateTopPadding() + calculateBottomPadding()