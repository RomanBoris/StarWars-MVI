package com.pobezhkin.starwars_mvi.heroes.mvi.middleware

import com.pobezhkin.starwars_mvi.core.network.NetworkResult
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest

class LoadHeroesMiddleware(
    private val repository: HeroesRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun invoke(
        effects: Flow<HeroesEffect>,
        state: Flow<HeroesState>
    ): Flow<HeroesEffect> = effects.filterIsInstance<HeroesEffect.List.Load>()
        .mapLatest {
            when (val result = repository.getAllHeroes()) {
                is NetworkResult.Success -> HeroesEffect.List.Loaded(
                    heroes = result.data,
                    fromCache = result.fromCache
                )
                is NetworkResult.Error -> HeroesEffect.List.LoadFailed(result.error)
            }
        }
}