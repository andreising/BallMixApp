package com.andreisingeleytsev.ballmixapp.domain.game_repository.usecases

import com.andreisingeleytsev.ballmixapp.data.game_manager.GameDifficulty
import com.andreisingeleytsev.ballmixapp.domain.game_repository.GameRepository
import javax.inject.Inject

class GetCupStatesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(): List<List<Int>> {
        return gameRepository.getCupStates()
    }
}