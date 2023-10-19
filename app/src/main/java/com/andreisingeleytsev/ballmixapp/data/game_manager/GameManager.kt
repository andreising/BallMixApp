package com.andreisingeleytsev.ballmixapp.data.game_manager



class GameManager {

    private var difficulty: GameDifficulty = GameDifficulty.Easy

    private val cups = mutableListOf<Int>()
    private val iterationList = mutableListOf<List<Int>>()

    fun shuffleCups() {
        iterationList.clear()
        iterationList.add(cups)
        for (i in 0 until 9){
            iterationList.add(cups.shuffled())
        }
    }

    fun getCupStates(): List<List<Int>> {
        return iterationList
    }

    fun setDifficulty(difficulty: GameDifficulty){
        this.difficulty = difficulty
        setNumberList(difficulty.index)
        shuffleCups()
    }

    private fun setNumberList(size: Int){
        cups.clear()
        for (i in 0 until size){
            cups.add(i)
        }
    }
}

sealed class GameDifficulty(val index: Int){
    data object Easy: GameDifficulty(index = 3)
    data object Normal: GameDifficulty(index = 4)
}