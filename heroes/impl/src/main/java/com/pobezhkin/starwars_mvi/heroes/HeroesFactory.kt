package com.pobezhkin.starwars_mvi.heroes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pobezhkin.starwars_mvi.heroes.api.HeroesScreenComponent

// Весь граф (сеть, база, middleware, Model) теперь собирает Dagger — этому классу остаётся
// только одна задача: мостик между ViewModelProvider.Factory (чего хочет Android)
// и HeroesScreenComponent.Factory (что умеет наш DI). Сравни с версией урока 12 —
// там же было ~50 строк ручной сборки, здесь одна строчка в create().
class HeroesFactory(
    private val factory: HeroesScreenComponent.Factory,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        factory.create().viewModel as T
}
