package io.github.mmolosay.playground.presentation.tv.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.github.mmolosay.playground.presentation.tv.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel.DataState

// TODO: v1 may shows portability pop-up in menu. It's not an appropriate place.

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
        MenuNavHost(
            appNavController = appNavController,
            menuNavController = menuNavController,
            menuState = mutableMenuState
        )

        Menu(
            modifier = Modifier.align(Alignment.TopStart),
            data = data,
            menuState = mutableMenuState,
        )
    }
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