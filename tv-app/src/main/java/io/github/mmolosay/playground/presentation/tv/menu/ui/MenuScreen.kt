package io.github.mmolosay.playground.presentation.tv.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel.DataState

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    appNavController: NavController,
) {
    val dataState = viewModel.dataStateFlow.collectAsStateWithLifecycle().value
    when (dataState) {
        is DataState.Loading -> {
            Unit // do nothing, will promptly change to different 'DataState'
        }
        is DataState.Ready -> {
            MenuScreen(
                data = dataState.data,
                appNavController = appNavController,
            )
        }
    }
}

@Composable
fun MenuScreen(
    data: MenuData,
    appNavController: NavController,
) {
    Box {
        val menuNavController = rememberNavController()
        val mutableMenuState = remember {
            MutableMenuState(
                isMenuOpen = data.isMenuOpen,
                toggleMenuAction = data.toggleMenu::invoke,
            )
        }
        val contentFocusRequester = remember { ContentFocusRequester(FocusRequester()) }
        MenuNavHost(
            appNavController = appNavController,
            menuNavController = menuNavController,
            menuState = mutableMenuState,
            contentFocusRequester = contentFocusRequester,
        )

        Row {
            Menu(
                data = data,
                menuState = mutableMenuState,
            )
            if (data.isMenuOpen) {
                val interactionSource = remember { MutableInteractionSource() }
                val isFocused = interactionSource.collectIsFocusedAsState().value
                val backgroundColor =
                    if (isFocused) PlaygroundTheme.colorScheme.focus
                    else MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
                Box(
                    modifier = Modifier
                        .weight(1f) // fill the rest of the width that's not occupied by menu
                        .fillMaxSize()
                        .background(backgroundColor)
                        .onFocusChanged {
                            if (it.isFocused) {
//                                try {
                                    contentFocusRequester.focusRequester.requestFocus()
//                                } catch (e: IllegalStateException) {
//                                    // focus requester is not attached
//                                    error("ContentFocusRequester is not attached")
//                                }
                            }
                        }
                        .focusable(),
                )
            }
        }
    }
}

/**
 * Focus requester that is attached to the content of the current destination of Menu NavHost.
 * Menu will request focus on it when it's being closed by moving focus out of it.
 */
@JvmInline
value class ContentFocusRequester(val focusRequester: FocusRequester)

@Stable
internal interface MenuState {
    val isMenuOpen: State<Boolean>
    val collapsedMenuWidth: State<Dp?>

    fun toggleMenu(open: Boolean)
}

@Stable
internal class MutableMenuState(
    isMenuOpen: Boolean,
    private val toggleMenuAction: (open: Boolean) -> Unit,
) : MenuState {
    override val isMenuOpen: MutableState<Boolean> = mutableStateOf(isMenuOpen)
    override val collapsedMenuWidth: MutableState<Dp?> = mutableStateOf(null)

    override fun toggleMenu(open: Boolean) =
        toggleMenuAction(open)
}