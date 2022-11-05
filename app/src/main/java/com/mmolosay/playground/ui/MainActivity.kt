package com.mmolosay.playground.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mmolosay.playground.ui.components.Main
import com.mmolosay.playground.ui.components.common.Screen
import com.mmolosay.playground.ui.theme.PlaygroundTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaygroundTheme {
                Screen {
                    Main()
                }
            }
        }
    }
}