package com.pobezhkin.starwars_mvi.heroes.data.remote.dto

import com.google.gson.annotations.SerializedName

data class StarHeroDto (

    val name: String?,
    val height: String?,
    val mass: String?,
    @SerializedName("hair_color")
    val hairColor: String?,
    @SerializedName("skin_color")
    val skinColor: String?,
    @SerializedName("eye_color")
    val eyeColor: String?,
    @SerializedName("birth_year")
    val birthYear: String?,
    val gender: String?,
    val homeworld: String?,
    val url: String?,
)