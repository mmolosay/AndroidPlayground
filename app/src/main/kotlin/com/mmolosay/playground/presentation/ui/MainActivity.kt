package com.mmolosay.playground.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mmolosay.playground.presentation.design.PlaygroundTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent() =
        setContent {
            PlaygroundTheme {
                MainScreen()
            }
        }
}