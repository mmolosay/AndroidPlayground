package com.mmolosay.playground.presentation.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mmolosay.playground.presentation.design.PlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent() =
        setContent {
            PlaygroundTheme {
                Application()
            }
        }

    @Composable
    private fun Application() {
        Scaffold { scaffoldPadding ->
            val navController = rememberNavController()
            MainNavHost(
                modifier = Modifier.padding(scaffoldPadding),
                navController = navController,
            )
        }
    }
}