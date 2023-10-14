package com.mmolosay.playground.presentation.ui

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmolosay.playground.presentation.design.PlaygroundTheme
import com.mmolosay.playground.presentation.ui.common.Screen
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalTime

// region Previews

@Preview(
//    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
)
@Composable
private fun MainPreview() {
    PlaygroundTheme {
        MainScreen(
            onButtonClick = { _, _ -> },
            buttonClickable = true,
        )
    }
}

// endregion

@Composable
fun MainScreen(
    onButtonClick: (String, (String) -> Unit) -> Unit,
    buttonClickable: Boolean,
) {
    Screen {
        Main(
            onButtonClick = onButtonClick,
            buttonClickable = buttonClickable,
        )
    }
}

@Composable
fun Main(
    onButtonClick: (String, (String) -> Unit) -> Unit,
    buttonClickable: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var text by remember { mutableStateOf("") }
        val events = remember { mutableStateListOf<String>() }
        val eventsListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(0.5f),
        )

        val onEvent: (String) -> Unit = { event ->
            val now = LocalTime.now().toString()
            val message = "$now: $event"
            events.add(message)
            coroutineScope.launch {
                eventsListState.scrollToItem(index = events.lastIndex)
            }
        }
        val onClick = {
            onButtonClick(text, onEvent)
        }
        val containerColor = MaterialTheme.colorScheme.run {
            if (buttonClickable) primary else primaryContainer
        }
        val buttonColors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        )

        Button(
            onClick = onClick,
            colors = buttonColors,
        ) {
            Text("Reverse input text")
        }

        LazyColumn(
            state = eventsListState,
            reverseLayout = true,
        ) {
            items(events) { event ->
                EventItem(event)
            }
        }
    }
}

@Composable
fun EventItem(event: String) {
    Text(text = event)
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
