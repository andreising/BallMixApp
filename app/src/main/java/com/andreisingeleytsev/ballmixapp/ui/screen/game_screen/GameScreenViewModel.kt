package com.andreisingeleytsev.ballmixapp.ui.screen.game_screen

import android.os.CountDownTimer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreisingeleytsev.ballmixapp.common.Constants
import com.andreisingeleytsev.ballmixapp.data.game_manager.GameDifficulty
import com.andreisingeleytsev.ballmixapp.domain.game_repository.usecases.GetCupStatesUseCase
import com.andreisingeleytsev.ballmixapp.domain.game_repository.usecases.SetGameDifficultyUseCase
import com.andreisingeleytsev.ballmixapp.domain.game_repository.usecases.StartRevengeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val getCupStatesUseCase: GetCupStatesUseCase,
    private val setGameDifficultyUseCase: SetGameDifficultyUseCase,
    private val startRevengeUseCase: StartRevengeUseCase,
    savedStateHandle: SavedStateHandle?
): ViewModel() {

    fun onEvent(event: GameScreenEvent){
        when(event){
            is GameScreenEvent.OnMixPressed -> {
                setGameState(GameState.OnShow)
                viewModelScope.launch {
                    barrelsUp()
                    delay(2000)
                    barrelsDown()
                    setGameState(GameState.OnShuffle)
                    shuffle()
                }
            }
            is GameScreenEvent.OnBarrelChose -> {
                    isWin = event.int==1
                    barrelsUp()
                    setGameState(GameState.Finish)
            }
            is GameScreenEvent.OnRestart -> {
                setGameState(GameState.Start)
                viewModelScope.launch {
                    barrelsDown()
                    startRevengeUseCase.invoke()
                    setData()
                }
            }
        }
    }

    private val _barrelsIsUp = mutableStateOf(false)
    val barrelsIsUp: State<Boolean> = _barrelsIsUp


    private val _gameState = mutableStateOf<GameState>(GameState.Start)
    val gameState: State<GameState> = _gameState

    var isWin = true

    var difficulty: GameDifficulty? = null
    private var cupStateList: List<List<Int>>? = null

    private val _currentList = mutableStateOf(emptyList<Int>())
    val currentList: State<List<Int>> = _currentList

    private fun setGameState(gameState: GameState){
        this._gameState.value = gameState
    }
    private suspend fun setDifficulty(difficultyIndex: Int){
        difficulty = when(difficultyIndex) {
            0 -> GameDifficulty.Easy
            1 -> GameDifficulty.Normal
            else -> null
        }
        difficulty?.let {
            setGameDifficultyUseCase.invoke(it)
            setData()
        }
    }

    private suspend fun setData(){
        cupStateList = getCupStatesUseCase.invoke()
        _currentList.value = cupStateList!!.first()
    }

    private fun barrelsUp(){
        this._barrelsIsUp.value = true
    }
    private fun barrelsDown(){
        this._barrelsIsUp.value = false
    }

    private suspend fun shuffle(){
        cupStateList!!.forEach {
            delay(700)
            _currentList.value = it
        }
        setGameState(GameState.OnChoose)
    }

    init {
        viewModelScope.launch {
            savedStateHandle?.get<String>(Constants.DIFFICULTY)?.toInt()?.let {
                setDifficulty(it)
            }
        }
    }

}

sealed class GameState{
    data object Start: GameState()
    data object OnShuffle: GameState()
    data object OnChoose: GameState()
    data object OnShow: GameState()
    data object Finish: GameState()
}