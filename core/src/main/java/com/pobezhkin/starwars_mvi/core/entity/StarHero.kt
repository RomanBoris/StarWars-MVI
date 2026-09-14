package com.pobezhkin.starwars_mvi.core.entity

data class StarHero(
    val url: String,
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val homeworld: String,
    val birthYear: String,
    val gender: String,
)