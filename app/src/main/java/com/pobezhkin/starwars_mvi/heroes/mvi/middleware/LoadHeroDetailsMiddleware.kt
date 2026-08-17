package com.pobezhkin.starwars_mvi.heroes.mvi.middleware

import com.pobezhkin.starwars_mvi.core.network.NetworkResult
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest

class LoadHeroDetailsMiddleware(
    private val repository: HeroesRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun invoke(
        effects: Flow<HeroesEffect>,
        states: Flow<HeroesState>
    ): Flow<HeroesEffect> = effects
        .filter { it is HeroesEffect.List.HeroClicked || it is HeroesEffect.Details.Retry }
        .mapLatest { effect ->

            val heroUrl = when (effect) {

                is HeroesEffect.List.HeroClicked -> effect.heroUrl

                is HeroesEffect.Details.Retry -> when (val step = states.first().step) {
                    is HeroesState.Step.DetailsLoading -> step.heroUrl
                    is HeroesState.Step.DetailsError -> step.heroUrl
                    else -> null
                }

                else -> null

            } ?: return@mapLatest null

            when (val result = repository.getHeroByUrl(heroUrl)) {
                is NetworkResult.Success -> HeroesEffect.Details.Loaded(
                    hero = result.data,
                    fromCache = result.fromCache
                )

                is NetworkResult.Error -> HeroesEffect.Details.LoadFailed(result.error)
            }

        }.filterNotNull()
}
