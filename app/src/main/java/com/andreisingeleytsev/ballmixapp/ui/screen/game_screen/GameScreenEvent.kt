package com.andreisingeleytsev.ballmixapp.ui.screen.game_screen

sealed class GameScreenEvent{
    data object OnMixPressed: GameScreenEvent()
    data class OnBarrelChose(val int: Int): GameScreenEvent()
    data object OnRestart: GameScreenEvent()
}
