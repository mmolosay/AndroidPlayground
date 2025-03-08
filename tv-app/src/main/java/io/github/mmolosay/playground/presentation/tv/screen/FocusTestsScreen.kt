package io.github.mmolosay.playground.presentation.tv.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.mmolosay.playground.presentation.tv.common.ButtonDefaultsUtil.focusAwareContainerColor
import io.github.mmolosay.playground.presentation.tv.common.FocusableElement
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Contains various UI controls to experiment with focus.
 */
@Composable
fun FocusTestsScreen(
    appNavController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var showFocusableElement by remember { mutableStateOf(true) }
        if (showFocusableElement) {
            FocusableElement {
                Text(
                    text = "Focusable element",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            val b1InteractionSource = remember { MutableInteractionSource() }
            Button(
                onClick = { appNavController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = focusAwareContainerColor(b1InteractionSource),
                ),
                interactionSource = b1InteractionSource,
            ) {
                Text("Go back")
            }

            val b2InteractionSource = remember { MutableInteractionSource() }
            Button(
                onClick = {
                    coroutineScope.launch {
                        delay(5.seconds)
                        showFocusableElement = !showFocusableElement
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = focusAwareContainerColor(b2InteractionSource),
                ),
                interactionSource = b2InteractionSource,
            ) {
                Text("Hide/show focusable element \nin 5 seconds")
            }

            val b3InteractionSource = remember { MutableInteractionSource() }
            Button(
                onClick = { focusManager.clearFocus() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = focusAwareContainerColor(b3InteractionSource),
                ),
                interactionSource = b3InteractionSource,
            ) {
                Text("Clear focus")
            }
        }
    }
}

@Composable
@Preview(device = "id:tv_1080p")
private fun Preview() {
    PlaygroundTheme {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        ) {
            FocusTestsScreen(
                appNavController = rememberNavController(),
            )
        }
    }
}