package com.pobezhkin.starwars_mvi.heroes.mvi

import com.pobezhkin.starwars_mvi.core.mvi.Reducer
import com.pobezhkin.starwars_mvi.heroes.mvi.subreducers.HeroDetailsReducer
import com.pobezhkin.starwars_mvi.heroes.mvi.subreducers.HeroListReducer

internal class HeroesReducer : Reducer<HeroesEffect, HeroesState> {

    private val listReducer = HeroListReducer()
    private val detailsReducer = HeroDetailsReducer()
    override fun invoke(effect: HeroesEffect, state: HeroesState): HeroesState =
        when(effect){
            is HeroesEffect.List -> listReducer(effect, state)
            is HeroesEffect.Details -> detailsReducer(effect, state)
        }

        companion object {
            fun initialState() = HeroesState(step = HeroesState.Step.ListLoading)
        }
}