package com.andreisingeleytsev.balanceapp.ui.utils

sealed class UIEvent{
    object OnBack: UIEvent()
    data class OnNavigate(val route: String): UIEvent()
}
