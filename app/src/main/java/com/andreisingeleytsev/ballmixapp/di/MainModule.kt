package com.andreisingeleytsev.ballmixapp.di

import com.andreisingeleytsev.ballmixapp.data.game_manager.GameManager
import com.andreisingeleytsev.ballmixapp.data.game_manager.implementation.GameRepositoryImpl
import com.andreisingeleytsev.ballmixapp.domain.game_repository.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideGameManager(): GameManager{
        return GameManager()
    }

    @Provides
    @Singleton
    fun provideGameRepository(gameManager: GameManager): GameRepository{
        return GameRepositoryImpl(gameManager)
    }


}