package io.github.mmolosay.playground.presentation.tv.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContent {
//            PlaygroundTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    shape = RectangleShape
//                ) {
//                    Greeting("Android")
//                }
//            }
//        }
    }
}