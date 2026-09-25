package com.pobezhkin.starwars_mvi.heroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pobezhkin.starwars_mvi.heroes.api.HeroesScreenComponent

class HeroesFactory(
    private val factory: HeroesScreenComponent.Factory,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        factory.create().viewModel as T
}
