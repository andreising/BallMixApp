package com.andreisingeleytsev.ballmixapp.domain.game_repository

import com.andreisingeleytsev.ballmixapp.data.game_manager.GameDifficulty

interface GameRepository {
    suspend fun setDifficulty(difficulty: GameDifficulty)
    suspend fun getCupStates(): List<List<Int>>
    suspend fun startRevenge()
}