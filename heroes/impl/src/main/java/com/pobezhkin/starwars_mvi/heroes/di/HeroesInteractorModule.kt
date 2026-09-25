package com.pobezhkin.starwars_mvi.heroes.di

import com.pobezhkin.starwars_mvi.core.di.scope.ViewModelScope
import com.pobezhkin.starwars_mvi.heroes.HeroesStateToViewStateMapper
import com.pobezhkin.starwars_mvi.heroes.HeroesViewModel
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesInteractor
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesModel
import com.pobezhkin.starwars_mvi.heroes.mvi.middleware.LoadHeroDetailsMiddleware
import com.pobezhkin.starwars_mvi.heroes.mvi.middleware.LoadHeroesMiddleware
import dagger.Module
import dagger.Provides

@Module
class HeroesInteractorModule {

    @Provides
    @ViewModelScope
    fun providesInteractor(
        dependencies: HeroesDependencies,
    ): HeroesInteractor = HeroesModel(
        middleware = setOf(
            LoadHeroesMiddleware(dependencies.heroesRepository),
            LoadHeroDetailsMiddleware(dependencies.heroesRepository),
        ),
        dispatchers = dependencies.dispatchers,
        logger = dependencies.logger,
    )

    @Provides
    @ViewModelScope
    fun providesViewModel(
        dependencies: HeroesDependencies,
        interactor: HeroesInteractor,
    ): HeroesViewModel = HeroesViewModel(
        interactor = interactor,
        stateToViewStateMapper = HeroesStateToViewStateMapper(dependencies.context),
    )
}
