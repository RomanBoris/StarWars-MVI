package com.pobezhkin.starwars_mvi.heroes

import androidx.lifecycle.ViewModel
import com.pobezhkin.starwars_mvi.heroes.api.HeroesScreenComponent
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesInteractor
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import kotlinx.coroutines.cancel

// : ViewModel() — androidx.lifecycle, жизненным циклом управляет Android (onCleared при закрытии экрана).
// : HeroesScreenComponent.ViewModel — маркер из heroes/api, по нему Dagger-сабкомпонент отдаёт этот класс наружу.
class HeroesViewModel(
    private val interactor : HeroesInteractor,
     val stateToViewStateMapper: (HeroesState) -> HeroesViewState
) : ViewModel(), HeroesScreenComponent.ViewModel {

    val stateFlow = interactor.stateFlow

    fun effect(effect: HeroesEffect) = interactor.effect(effect)

    override fun onCleared() {
        interactor.cancel()
        super.onCleared()
    }
}