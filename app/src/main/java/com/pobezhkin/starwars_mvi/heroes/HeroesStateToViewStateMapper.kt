package com.pobezhkin.starwars_mvi.heroes

import android.content.Context
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState

import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState

import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState.Step
import com.pobezhkin.starwars_mvi.heroes.submappers.HeroDetailsToViewStateMapper
import com.pobezhkin.starwars_mvi.heroes.submappers.HeroListToViewStateMapper
import com.pobezhkin.starwars_mvi.heroes.submappers.NetworkErrorTextMapper

class HeroesStateToViewStateMapper(
    val context: Context
) : (HeroesState) -> HeroesViewState {

    private val listMapper = HeroListToViewStateMapper(context)
    private val detailsMapper = HeroDetailsToViewStateMapper(context)
    private val errorTextMapper = NetworkErrorTextMapper(context)

    override fun invoke(state: HeroesState) = HeroesViewState(
        step = when (val step = state.step) {
            is Step.ListLoading,
            is Step.DetailsLoading -> StepViewState.Loading


            is Step.ListError -> StepViewState.Error(
                message = errorTextMapper(step.error),
                showBack = false
            )

        is Step.DetailsError -> StepViewState.Error(
            message = errorTextMapper(step.error),
            showBack = true
        )
        is Step.HeroList -> listMapper(step)

        is Step.Details -> detailsMapper(step)

        }
    )
}