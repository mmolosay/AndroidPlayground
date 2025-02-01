package com.mmolosay.playground.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.presentation.design.PlaygroundTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

    }
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
