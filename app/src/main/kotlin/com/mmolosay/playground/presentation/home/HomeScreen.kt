package com.mmolosay.playground.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.presentation.design.PlaygroundTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
//    viewModel: HomeViewModel,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val coroutineScope = rememberCoroutineScope()
            val animator = remember { CircularRevealAnimator() }
            CircularReveal(
                animator = animator,
                startContent = {
                    ContentA()
                },
                endContent = {
                    ContentB()
                },
                position = { size -> Offset(x = size.width / 2, y = size.height * 0.25f) },
            )

            Row {
                Button(onClick = {
                    coroutineScope.launch {
                        animator.expand()
                    }
                }) {
                    Text(text = "Expand")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        animator.collapse()
                    }
                }) {
                    Text(text = "Collapse")
                }
            }
        }
    }
}

@Composable
private fun ContentA() {
    ContentLayout(
        modifier = Modifier.background(Color.Blue),
    ) {
        Text(
            text = "This is Content A, my friend",
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
        )
    }
}

@Composable
private fun ContentB() {
    ContentLayout(
        modifier = Modifier.background(Color.Green),
    ) {
        Text(
            text = "Content B this is, friend of mine",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displayLarge,
        )
    }
}

@Composable
private fun ContentLayout(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}

@Preview(
    showBackground = true,
)
@Composable
private fun MainPreview() {
    PlaygroundTheme {
//        HomeScreen()
    }
}
