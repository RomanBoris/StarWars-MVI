package com.pobezhkin.starwars_mvi.heroes

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
data class HeroesViewState(
    val step: StepViewState
){
    @Stable
    sealed class StepViewState{
        data object Loading: StepViewState()

        @Stable
        data class Error(
            val message: String,
            val showBack: Boolean
        ): StepViewState()

        @Stable
        data class HeroList(
            val heroes: ImmutableList<HeroCardViewState>,
            val offlineBanner: String?,
            val scrollSnapshot: ScrollSnapshot?,
        ): StepViewState(){
            @Immutable
            data class HeroCardViewState(
                val url: String,
                val name: String,
                val subtitle: String
            )
            @Immutable
            data class ScrollSnapshot(
                val firstVisibleItemIndex: Int,
                val firstVisibleItemScrollOffset: Int
            )
        }

        @Stable
        data class HeroDetails(
            val title: String,
            val rows: ImmutableList<DetailRow>,
            val offlineBanner: String?
        ): StepViewState(){
            @Immutable
            data class DetailRow(val label: String, val value: String)
        }
    }
}
