package com.pobezhkin.starwars_mvi.heroes.di

import android.content.Context
import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import com.pobezhkin.starwars_mvi.core.feature.Component
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.heroes.api.HeroesApi
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class HeroesModule {

    @Singleton
    @Provides
    fun providesHeroesDependencies(
        context: Context,
        dispatchers: AppDispatchers,
        logger: AppLogger,
        heroesRepository: HeroesRepository,
    ): HeroesDependencies = object : HeroesDependencies {
        override val context = context
        override val dispatchers = dispatchers
        override val logger = logger
        override val heroesRepository = heroesRepository
    }

    @Provides
    fun providesHeroesApi(dependencies: HeroesDependencies): HeroesApi =
        HeroesContainer.run {
            init(dependencies)
            provide()
        }

    @Provides
    @IntoMap
    @ClassKey(HeroesApi::class)
    fun providesIntoMap(api: HeroesApi): Component = api
}
