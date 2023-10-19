package com.andreisingeleytsev.ballmixapp.data.game_manager.implementation

import com.andreisingeleytsev.ballmixapp.data.game_manager.GameDifficulty
import com.andreisingeleytsev.ballmixapp.data.game_manager.GameManager
import com.andreisingeleytsev.ballmixapp.domain.game_repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(
    private val gameManager: GameManager
):GameRepository {
    override suspend fun setDifficulty(difficulty: GameDifficulty) {
        gameManager.setDifficulty(difficulty)
    }

    override suspend fun getCupStates(): List<List<Int>> {
        return gameManager.getCupStates()
    }

    override suspend fun startRevenge() {
        gameManager.shuffleCups()
    }
}