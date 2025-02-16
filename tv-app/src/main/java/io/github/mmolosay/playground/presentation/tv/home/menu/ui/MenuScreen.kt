package io.github.mmolosay.playground.presentation.tv.home.menu.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuData
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuViewModel
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuViewModel.DataState

// TODO: v1 may shows portability pop-up in menu. It's not an appropriate place.

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
) {
    val dataState = viewModel.dataStateFlow.collectAsStateWithLifecycle().value
    when (dataState) {
        is DataState.Loading -> {
            Unit // do nothing, will promptly change to different 'DataState'
        }
        is DataState.Ready -> {
            MenuScreen(
                data = dataState.data,
            )
        }
    }
}

@Composable
fun MenuScreen(
    data: MenuData,
) {
    Box {
        val menuNavController = rememberNavController()
        MenuNavHost(
            navController = menuNavController,
        )

        Menu(
            modifier = Modifier.align(Alignment.TopStart),
            data = data,
        )
    }
}