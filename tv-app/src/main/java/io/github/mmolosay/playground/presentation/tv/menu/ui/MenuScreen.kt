package io.github.mmolosay.playground.presentation.tv.menu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.focusGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.mmolosay.playground.presentation.tv.design.PlaygroundTheme
import io.github.mmolosay.playground.presentation.tv.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel.DataState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

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
        val contentFocusRequester = remember { ContentFocusRequester() }

        MenuNavHost(
            appNavController = appNavController,
            menuNavController = menuNavController,
            menuState = mutableMenuState,
            contentFocusRequester = contentFocusRequester,
        )
        DecoratedMenu(
            data = data,
            menuState = mutableMenuState,
            contentFocusRequester = contentFocusRequester,
        )
    }
}

@Composable
private fun DecoratedMenu(
    data: MenuData,
    menuState: MutableMenuState,
    contentFocusRequester: ContentFocusRequester,
) {
    val negativeSpaceInteractionSource = remember { MutableInteractionSource() }
    val isNegativeSpaceFocused = negativeSpaceInteractionSource.collectIsFocusedAsState().value
    Row(
        modifier = Modifier
            .onFocusChanged {
                data.toggleMenu(open = it.hasFocus)
            }
            .focusGroup(),
    ) {
        Menu(
            modifier = Modifier
                .onFocusChanged {
                    if (isNegativeSpaceFocused) return@onFocusChanged
                    data.toggleMenu(open = it.hasFocus)
                },
            data = data,
            menuState = menuState,
        )
        if (data.isMenuOpen) {
            MenuNegativeSpace(
                modifier = Modifier.weight(1f), // fill the rest of the width that's not occupied by menu
                contentFocusRequester = contentFocusRequester,
                interactionSource = negativeSpaceInteractionSource,
            )
        }
    }
}

@Composable
private fun MenuNegativeSpace(
    modifier: Modifier,
    contentFocusRequester: ContentFocusRequester,
    interactionSource: MutableInteractionSource,
) {
    val isFocused = interactionSource.collectIsFocusedAsState().value
    val backgroundColor =
        if (isFocused) PlaygroundTheme.colorScheme.focus
        else MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(backgroundColor)
            .onFocusChanged {
                if (it.isFocused) {
                    contentFocusRequester.requestFocus()
                }
            }
            .focusable(interactionSource = interactionSource),
    )
}

/**
 * Focus requester that is attached to the content of the current destination of Menu NavHost.
 * Menu will request focus on it when it's being closed by moving focus out of it.
 */
@Immutable
class ContentFocusRequester {

    private val _eventFlow = MutableSharedFlow<RequestFocusEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val eventFlow = _eventFlow.asSharedFlow()

    fun requestFocus() {
        val event = RequestFocusEvent
        val emissionResult = _eventFlow.tryEmit(event)
        if (!emissionResult) error("Failed to emit $event")
    }

    data object RequestFocusEvent
}

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