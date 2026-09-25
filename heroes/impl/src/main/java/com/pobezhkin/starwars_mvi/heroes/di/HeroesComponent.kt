package com.pobezhkin.starwars_mvi.heroes.di

import com.pobezhkin.starwars_mvi.core.di.scope.FeatureScope
import com.pobezhkin.starwars_mvi.heroes.api.HeroesApi
import dagger.Component

@FeatureScope
@Component(
    modules = [HeroesViewModule::class],
    dependencies = [HeroesDependencies::class],
)
internal abstract class HeroesComponent : HeroesApi {

    @Component.Factory
    interface Factory {
        fun create(dependencies: HeroesDependencies): HeroesComponent
    }

    companion object {
        fun create(dependencies: HeroesDependencies): HeroesComponent =
            DaggerHeroesComponent.factory().create(dependencies)
    }
}
