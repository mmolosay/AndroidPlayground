package io.github.mmolosay.playground.presentation.tv.home.menu

data class MenuData(
    val mainMenu: MainMenu,
    val sportMenu: SportMenu,
    val menuState: MenuState,

    val toggleMenu: ToggleMenuStateAction,
    val toggleSportMenu: ToggleMenuStateAction,
) {

    data class MainMenu(
        val selectedItem: MainMenuItem,
        val items: List<MainMenuItem>,
        val selectItem: SelectItemAction,
    ) {
        fun interface SelectItemAction {
            operator fun invoke(item: MainMenuItem)
        }
    }

    data class SportMenu(
        val selectedItem: SportMenuItem?,
        val items: List<SportMenuItem>,
        val selectItem: SelectItemAction,
    ) {
        fun interface SelectItemAction {
            operator fun invoke(item: SportMenuItem)
        }
    }

    fun interface ToggleMenuStateAction {
        operator fun invoke(open: Boolean)
    }
}

data class MainMenuItem(
    val type: Type,
    val title: String,
) {
    enum class Type {
        Home,
        Sports,
        Schedule,
        LiveTv,
        Settings,
    }
}

data class SportMenuItem(
    val id: String,
    val title: String,
)

data class MenuState(
    val isMenuOpen: Boolean,
    val isSportMenuOpen: Boolean,
)