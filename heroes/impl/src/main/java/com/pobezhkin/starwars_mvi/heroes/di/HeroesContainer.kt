package com.pobezhkin.starwars_mvi.heroes.di

import com.pobezhkin.starwars_mvi.core.feature.DoubleCheckContainer
import com.pobezhkin.starwars_mvi.heroes.api.HeroesApi

internal object HeroesContainer :
    DoubleCheckContainer<HeroesApi, HeroesDependencies>({ HeroesComponent.create(it) })
