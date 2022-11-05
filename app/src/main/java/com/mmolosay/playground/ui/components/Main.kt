package com.mmolosay.playground.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.ui.components.common.Screen
import com.mmolosay.playground.ui.theme.PlaygroundTheme

// region Previews

@Preview(
//    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
private fun MainPreview() {
    PlaygroundTheme {
        Screen {
            Main()
        }
    }
}

// endregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main() {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "",
                            modifier = Modifier.size(width = 120.dp, height = 40.dp)
                        )
                    }
                )
            }
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            // empty
        }
    }
}

@Composable
private fun Content() =
    Box(
        modifier = Modifier
            .size(6.dp)
            .background(Color(0xFFCBE6C6))
    )