package com.pobezhkin.starwars_mvi.heroes.submappers

import android.content.Context
import com.pobezhkin.starwars_mvi.R
import com.pobezhkin.starwars_mvi.heroes.HeroesViewState.StepViewState
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState.Step
import kotlinx.collections.immutable.persistentListOf

class HeroDetailsToViewStateMapper(
    private val context: Context
) : (Step.Details) -> StepViewState.HeroDetails {
    override fun invoke(step: Step.Details) = StepViewState.HeroDetails(
        title = step.hero.name,
        rows = persistentListOf(
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_height),
                step.hero.height
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_mass),
                step.hero.mass
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_hair_color),
                step.hero.hairColor
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_skin_color),
                step.hero.skinColor
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_eye_color),
                step.hero.eyeColor
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_homeworld),
                step.hero.homeworld
            ),
            StepViewState.HeroDetails.DetailRow(
                context.getString(R.string.hero_gender),
                step.hero.gender
            )
        ),
        offlineBanner = if (step.fromCache) context.getString(R.string.offline_banner) else null,
    )
}