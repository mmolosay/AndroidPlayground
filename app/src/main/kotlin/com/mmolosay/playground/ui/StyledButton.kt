package com.mmolosay.playground.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import com.mmolosay.playground.design.PlaygroundTheme

// region Previews

@Preview
@Composable
private fun StyledButtonPreview() =
    PlaygroundTheme {
        StyledButton(onClick = {}) {
            Text("StyledButton Preview")
        }
    }

// endregion

/**
 * [Button] with styled appearance provided by [StyledButtonDefaults].
 */
@Composable
fun StyledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = StyledButtonDefaults.Shape,
    colors: ButtonColors = StyledButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = StyledButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = StyledButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit,
) =
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content,
    )

object StyledButtonDefaults {

    // naming: https://android.googlesource.com/platform/frameworks/support/+/androidx-main/compose/docs/compose-api-guidelines.md#singletons_constants_sealed-class-and-enum-class-values
    val Shape: Shape by lazy {
        CutCornerShape(25)
    }

    val ContentPadding: PaddingValues by lazy {
        ButtonDefaults.ContentPadding
    }

    @Composable
    fun buttonColors(): ButtonColors {
        val containerColor = MaterialTheme.colorScheme.tertiaryContainer
        return ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColorFor(containerColor),
        )
    }

    @Composable
    fun buttonElevation() =
        ButtonDefaults.buttonElevation()
}