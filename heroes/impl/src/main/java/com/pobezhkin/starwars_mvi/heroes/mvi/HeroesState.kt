package com.pobezhkin.starwars_mvi.heroes.mvi

import com.pobezhkin.starwars_mvi.core.entity.StarHero
import com.pobezhkin.starwars_mvi.core.network.NetworkError

data class HeroesState(
    val step: Step,
    val previousListStep: Step.HeroList? = null
) {
    sealed class Step {
        data object ListLoading : Step()

        data class ListError(val error: NetworkError) : Step()

        data class HeroList(
            val heroes: List<StarHero>,
            val fromCache: Boolean,
            val scrollSnapshot: ScrollSnapshot? = null
        ) : Step() {
            data class ScrollSnapshot(
                val firstVisibleItemIndex: Int,
                val firstVisibleItemScrollOffset: Int
            )
        }

        data class DetailsLoading(
            val heroUrl: String
        ) : Step()

        data class DetailsError(
            val heroUrl: String,
            val error: NetworkError
        ) : Step()

        data class Details(
            val hero: StarHero,
            val fromCache: Boolean
        ) : Step()
    }
}
