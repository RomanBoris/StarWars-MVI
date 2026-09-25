package com.pobezhkin.starwars_mvi.di

import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchersImpl
import com.pobezhkin.starwars_mvi.core.log.AndroidLogger
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository
import com.pobezhkin.starwars_mvi.heroes.data.HeroesRepositoryImpl
import com.pobezhkin.starwars_mvi.heroes.data.local.StarHeroDao
import com.pobezhkin.starwars_mvi.heroes.data.remote.StarApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// Общие для всего приложения вещи: диспетчеры, логгер и репозиторий героев
// (interface из heroes/api → implementation из heroes/data, собраны здесь, а не в фиче —
// фиче Dagger отдаст уже готовый HeroesRepository через HeroesDependencies).
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesDispatchers(): AppDispatchers = AppDispatchersImpl()

    @Provides
    @Singleton
    fun providesLogger(): AppLogger = AndroidLogger()

    @Provides
    @Singleton
    fun providesHeroesRepository(
        api: StarApiService,
        dao: StarHeroDao,
        logger: AppLogger,
    ): HeroesRepository = HeroesRepositoryImpl(api, dao, logger)
}
