package com.pobezhkin.starwars_mvi.heroes.submappers

import android.content.Context
import com.pobezhkin.starwars_mvi.R

import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState.Step
import kotlinx.collections.immutable.toImmutableList

class HeroListToViewStateMapper(
    private val context: Context
) : (Step.HeroList) -> StepViewState.HeroList {
    override fun invoke(step: Step.HeroList) = StepViewState.HeroList(
        heroes = step.heroes.map { hero ->
        StepViewState.HeroList.HeroCardViewState(
            url = hero.url,
            name = hero.name,
            subtitle = context.getString(
                R.string.hero_card_subtitle, hero.height, hero.birthYear
            ),
        )
        }.toImmutableList(),
        offlineBanner = if (step.fromCache) context.getString(R.string.offline_banner) else null,
        scrollSnapshot = step.scrollSnapshot?.let {
            StepViewState.HeroList.ScrollSnapshot(
                firstVisibleItemIndex = it.firstVisibleItemIndex,
                firstVisibleItemScrollOffset = it.firstVisibleItemScrollOffset
            )
        },
    )
}