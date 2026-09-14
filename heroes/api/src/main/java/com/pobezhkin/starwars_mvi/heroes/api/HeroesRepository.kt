package com.pobezhkin.starwars_mvi.heroes.api

import com.pobezhkin.starwars_mvi.core.entity.StarHero
import com.pobezhkin.starwars_mvi.core.network.NetworkResult

interface HeroesRepository {
    suspend fun getAllHeroes(): NetworkResult<List<StarHero>>
    suspend fun getHeroByUrl(url: String): NetworkResult<StarHero>
}