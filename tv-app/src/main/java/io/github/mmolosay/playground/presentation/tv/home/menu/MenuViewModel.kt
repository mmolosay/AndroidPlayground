package io.github.mmolosay.playground.presentation.tv.home.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.playground.presentation.tv.home.menu.MenuViewModel.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {

    private val _dataStateFlow = MutableStateFlow<DataState>(DataState.Loading)
    val dataStateFlow = _dataStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            delay(2.seconds) // simulates data fetching from server
            _dataStateFlow.value = DataState.Ready(data = makeData())
        }
    }

    private fun makeData(): MenuData {
        val mainMenu = kotlin.run {
            val items = listOf(
                MainMenuItem(
                    type = MainMenuItem.Type.Home,
                    title = "Home",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Sports,
                    title = "Sports",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Schedule,
                    title = "Schedule",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.LiveTv,
                    title = "Live TV",
                ),
                MainMenuItem(
                    type = MainMenuItem.Type.Settings,
                    title = "Settings",
                ),
            )
            MenuData.MainMenu(
                selectedItem = items.first(),
                items = items,
                selectItem = ::setSelectedMainMenuItem,
            )
        }
        val sportMenu = kotlin.run {
            val items = listOf(
                SportMenuItem(
                    id = "nfl",
                    title = "NFL",
                ),
                SportMenuItem(
                    id = "boxing",
                    title = "Boxing",
                ),
                SportMenuItem(
                    id = "women-football",
                    title = "Women's Football",
                ),
                SportMenuItem(
                    id = "soccer",
                    title = "Soccer",
                ),
                SportMenuItem(
                    id = "mma",
                    title = "MMA",
                ),
                SportMenuItem(
                    id = "smth-longer",
                    title = "Something longer",
                ),
                SportMenuItem(
                    id = "smth-even-more-longer",
                    title = "Something even more longer",
                ),
            )
            MenuData.SportMenu(
                selectedItem = items.first(),
                items = items,
                selectItem = ::setSelectedSportMenuItem,
            )
        }
        return MenuData(
            mainMenu = mainMenu,
            sportMenu = sportMenu,
            menuState = MenuState(
                isMenuOpen = false,
                isSportMenuOpen = false,
            ),
            toggleMenu = ::toggleMenu,
            toggleSportMenu = ::toggleSportMenu,
        )
    }

    private fun setSelectedMainMenuItem(item: MainMenuItem) {
        _dataStateFlow.updateData { currentData ->
            val newMainMenu = currentData.mainMenu.copy(selectedItem = item)
            return@updateData currentData.copy(mainMenu = newMainMenu)
        }
    }

    private fun setSelectedSportMenuItem(item: SportMenuItem) {
        _dataStateFlow.updateData { currentData ->
            val newSportMenu = currentData.sportMenu.copy(selectedItem = item)
            return@updateData currentData.copy(sportMenu = newSportMenu)
        }
    }

    private fun toggleMenu(open: Boolean) {
        _dataStateFlow.updateData { currentData ->
            val newMenuState = currentData.menuState.copy(isMenuOpen = open)
            return@updateData currentData.copy(menuState = newMenuState)
        }
    }

    private fun toggleSportMenu(open: Boolean) {
        _dataStateFlow.updateData { currentData ->
            val newMenuState = currentData.menuState.copy(isSportMenuOpen = open)
            return@updateData currentData.copy(menuState = newMenuState)
        }
    }

    sealed interface DataState {
        data object Loading : DataState
        data class Ready(val data: MenuData) : DataState
    }
}

private inline fun MutableStateFlow<DataState>.updateData(
    mutate: (currentData: MenuData) -> MenuData,
) =
    this.update { dataState ->
        (dataState as? DataState.Ready) ?: return@update dataState
        val currentData = dataState.data
        val newData = mutate(currentData)
        return@update DataState.Ready(newData)
    }