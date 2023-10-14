package com.mmolosay.playground.presentation.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mmolosay.playground.presentation.design.PlaygroundTheme
import io.github.mmolosay.debounce.debounce
import io.github.mmolosay.debounce.identity.DebounceStateIdentity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity() {

    private val buttonState = DebounceStateIdentity()
    private val buttonClickableFlow = MutableStateFlow(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent() =
        setContent {
            PlaygroundTheme {
                MainScreen(
                    onButtonClick = ::onButtonClick,
                    buttonClickable = buttonClickableFlow.collectAsStateWithLifecycle().value,
                )
            }
        }

    private fun onButtonClick(input: String, onEvent: (String) -> Unit) {
        onEvent("click, attempt")
        val timeout = 2.seconds
        val result = buttonState.debounce {
            buttonClickableFlow.value = false
            showToast("reversing...")
            lifecycleScope.launch(Dispatchers.Default) {
                delay(4.seconds)
                val reversed = input.reversed()
                val message = "$input – $reversed"
                withContext(Dispatchers.Main.immediate) { showToast(message) }
                onEvent("done: $message")

                releaseIn(timeout)
                delay(timeout)
                buttonClickableFlow.value = true
                onEvent("released")
            }
        }
        if (result) {
            onEvent("executed")
        } else {
            onEvent("debounced")
        }
    }
}