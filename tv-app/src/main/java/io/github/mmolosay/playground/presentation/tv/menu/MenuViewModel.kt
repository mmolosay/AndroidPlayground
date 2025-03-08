package io.github.mmolosay.playground.presentation.tv.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mmolosay.playground.presentation.tv.menu.MenuViewModel.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {

    private val _dataStateFlow = MutableStateFlow<DataState>(DataState.Loading)
    val dataStateFlow = _dataStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _dataStateFlow.value = DataState.Ready(data = makeData())
        }
    }

    @Suppress("SpellCheckingInspection")
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
                    title = "1. NFL",
                ),
                SportMenuItem(
                    id = "boxing",
                    title = "2. Boxing",
                ),
                SportMenuItem(
                    id = "women-football",
                    title = "3. Women's Football",
                ),
                SportMenuItem(
                    id = "soccer",
                    title = "4. Soccer",
                ),
                SportMenuItem(
                    id = "mma",
                    title = "5. MMA",
                ),
                SportMenuItem(
                    id = "smth-longer",
                    title = "6. Something longer",
                ),
                SportMenuItem(
                    id = "smth-even-more-longer",
                    title = "7. Something even more longer",
                ),
                SportMenuItem(
                    id = "8",
                    title = "8. Lorem",
                ),
                SportMenuItem(
                    id = "9",
                    title = "9. ipsum",
                ),
                SportMenuItem(
                    id = "10",
                    title = "10. dolor sit",
                ),
                SportMenuItem(
                    id = "11",
                    title = "11. amet",
                ),
                SportMenuItem(
                    id = "12",
                    title = "12. consectetur",
                ),
                SportMenuItem(
                    id = "13",
                    title = "13. adipiscing",
                ),
                SportMenuItem(
                    id = "14",
                    title = "14. elit",
                ),
                SportMenuItem(
                    id = "15",
                    title = "15. Sed",
                ),
                SportMenuItem(
                    id = "16",
                    title = "16. dictum",
                ),
                SportMenuItem(
                    id = "17",
                    title = "17. dui nec",
                ),
                SportMenuItem(
                    id = "18",
                    title = "18. placerat",
                ),
                SportMenuItem(
                    id = "19",
                    title = "19. sollicitudin",
                ),
                SportMenuItem(
                    id = "20",
                    title = "20. magna ",
                ),
            )
            MenuData.SportMenu(
                isOpen = false,
                selectedItem = items.first(),
                items = items,
                selectItem = ::setSelectedSportMenuItem,
            )
        }
        return MenuData(
            isMenuOpen = false,
            mainMenu = mainMenu,
            sportMenu = sportMenu,
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
            return@updateData currentData.copy(isMenuOpen = open)
        }
    }

    private fun toggleSportMenu(open: Boolean) {
        _dataStateFlow.updateData { currentData ->
            val newSportMenu = currentData.sportMenu.copy(isOpen = open)
            return@updateData currentData.copy(sportMenu = newSportMenu)
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