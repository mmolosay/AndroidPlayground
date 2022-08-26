package com.mmolosay.playground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.mmolosay.playground.design.PlaygroundTheme
import com.mmolosay.playground.ui.MainScreen

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