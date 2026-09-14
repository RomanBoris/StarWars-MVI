package com.pobezhkin.starwars_mvi.heroes.mvi

import com.pobezhkin.starwars_mvi.core.entity.StarHero
import com.pobezhkin.starwars_mvi.core.network.NetworkError

sealed class HeroesEffect{

    sealed class List: HeroesEffect(){
        data object Load: List()
        data class Loaded(
            val heroes: kotlin.collections.List<StarHero>,
            val fromCache : Boolean,

        ): List()

        data class LoadFailed(val error: NetworkError): List()

        data class HeroClicked(
            val heroUrl: String,
            val firstVisibleItemIndex: Int,
            val firstVisibleItemScrollOffset: Int
        ): List()
    }

    sealed class Details: HeroesEffect(){

        data class Loaded(val hero: StarHero, val fromCache: Boolean): Details()
        data class LoadFailed(val error: NetworkError): Details()
        data object Retry: Details()
        data object Back: Details()

    }

}