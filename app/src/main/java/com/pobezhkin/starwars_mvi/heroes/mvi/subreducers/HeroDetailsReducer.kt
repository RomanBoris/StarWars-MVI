package com.pobezhkin.starwars_mvi.heroes.mvi.subreducers

import com.pobezhkin.starwars_mvi.core.mvi.Reducer
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState.Step

internal class HeroDetailsReducer: Reducer<HeroesEffect.Details, HeroesState> {
    override fun invoke(
        effect: HeroesEffect.Details,
        state: HeroesState): HeroesState =
        when(effect){
            is HeroesEffect.Details.Loaded -> when(state.step){
                is Step.DetailsLoading -> state.copy(
                    step = Step.Details(effect.hero, effect.fromCache)
                )
                else -> state
            }

            is HeroesEffect.Details.LoadFailed -> when(val current = state.step){
                is Step.DetailsLoading -> state.copy(
                    step = Step.DetailsError(heroUrl = current.heroUrl, error = effect.error)
                )
                else -> state
            }

            is HeroesEffect.Details.Retry -> when(val current = state.step){
                is Step.DetailsError -> state.copy(step = Step.DetailsLoading(current.heroUrl))
                else -> state
            }

            is HeroesEffect.Details.Back -> state.copy(
                step = state.previousListStep ?: Step.ListLoading,
                previousListStep = null
            )

    }
}