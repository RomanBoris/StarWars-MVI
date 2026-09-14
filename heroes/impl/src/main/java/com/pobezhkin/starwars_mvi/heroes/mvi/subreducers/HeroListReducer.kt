package com.pobezhkin.starwars_mvi.heroes.mvi.subreducers

import com.pobezhkin.starwars_mvi.core.mvi.Reducer
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState.Step

internal class HeroListReducer : Reducer<HeroesEffect.List, HeroesState> {
    override fun invoke(effect: HeroesEffect.List, state: HeroesState): HeroesState =
        when (effect) {

            is HeroesEffect.List.Load -> state.copy(step = HeroesState.Step.ListLoading)

            is HeroesEffect.List.Loaded -> state.copy(
                step = HeroesState.Step.HeroList(
                    heroes = effect.heroes,
                    fromCache = effect.fromCache
                )
            )

            is HeroesEffect.List.LoadFailed -> state.copy(step = Step.ListError(effect.error))

            is HeroesEffect.List.HeroClicked -> {
                val currentList = state.step as? Step.HeroList

                state.copy(
                    step = Step.DetailsLoading(effect.heroUrl),

                    previousListStep = currentList?.copy(
                        scrollSnapshot = Step.HeroList.ScrollSnapshot(
                            firstVisibleItemIndex = effect.firstVisibleItemIndex,
                            firstVisibleItemScrollOffset = effect.firstVisibleItemScrollOffset
                        )
                    ),
                )
            }
        }
}