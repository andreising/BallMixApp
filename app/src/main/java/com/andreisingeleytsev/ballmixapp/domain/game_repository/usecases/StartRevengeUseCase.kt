package com.andreisingeleytsev.ballmixapp.domain.game_repository.usecases

import com.andreisingeleytsev.ballmixapp.data.game_manager.GameDifficulty
import com.andreisingeleytsev.ballmixapp.domain.game_repository.GameRepository
import javax.inject.Inject

class StartRevengeUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke() {
        gameRepository.startRevenge()
    }
}