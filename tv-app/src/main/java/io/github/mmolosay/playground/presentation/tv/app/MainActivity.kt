package io.github.mmolosay.playground.presentation.tv.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.design.Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.setBackgroundDrawable(null)
        setContent()
    }

    private fun enableEdgeToEdge() =
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT,
            ),
        )

    private fun setContent() =
        setContent {
            PlaygroundTheme(theme = Theme.Dark) {
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                ) {
                    val navController = rememberNavController()
                    MainNavHost(
                        navController = navController,
                    )
                }
            }
        }
}