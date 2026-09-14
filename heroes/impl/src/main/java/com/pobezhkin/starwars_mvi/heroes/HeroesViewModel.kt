package com.pobezhkin.starwars_mvi.heroes

import androidx.lifecycle.ViewModel
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesInteractor
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import kotlinx.coroutines.cancel

class HeroesViewModel(
    private val interactor : HeroesInteractor,
     val stateToViewStateMapper: (HeroesState) -> HeroesViewState
) : ViewModel() {

    val stateFlow = interactor.stateFlow

    fun effect(effect: HeroesEffect) = interactor.effect(effect)

    override fun onCleared() {
        interactor.cancel()
        super.onCleared()
    }
}