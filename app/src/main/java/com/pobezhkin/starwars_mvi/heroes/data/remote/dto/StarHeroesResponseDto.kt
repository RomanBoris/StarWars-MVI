package com.pobezhkin.starwars_mvi.heroes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StarHeroesResponseDto (
    val count: Int?,
    val next: String?,
    val previous: String?,
    @SerializedName("results")
    val resultsHeroes: List<StarHeroDto>,
)