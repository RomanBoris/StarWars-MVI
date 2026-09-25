package com.pobezhkin.starwars_mvi.heroes.di

import android.content.Context
import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import com.pobezhkin.starwars_mvi.core.feature.Dependencies
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository

interface HeroesDependencies : Dependencies {
    val context: Context
    val dispatchers: AppDispatchers
    val logger: AppLogger
    val heroesRepository: HeroesRepository
}
