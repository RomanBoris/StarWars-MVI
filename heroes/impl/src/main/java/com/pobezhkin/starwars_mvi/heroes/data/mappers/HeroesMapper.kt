package com.pobezhkin.starwars_mvi.heroes.data.mappers

import com.pobezhkin.starwars_mvi.core.entity.StarHero
import com.pobezhkin.starwars_mvi.heroes.data.local.StarHeroEntity
import com.pobezhkin.starwars_mvi.heroes.data.remote.dto.StarHeroDto

fun StarHeroDto.toDomain() = StarHero(
    url = url.orEmpty(),
    name = name.orEmpty(),
    height = height.orEmpty(),
    mass = mass.orEmpty(),
    hairColor = hairColor.orEmpty(),
    skinColor = skinColor.orEmpty(),
    eyeColor = eyeColor.orEmpty(),
    homeworld = homeworld.orEmpty(),
    birthYear = birthYear.orEmpty(),
    gender = gender.orEmpty()
)

fun StarHero.toEntity() = StarHeroEntity(
    url = url,
    name = name,
    height = height,
    mass = mass,
    hairColor = hairColor,
    skinColor = skinColor,
    eyeColor = eyeColor,
    homeworld = homeworld,
    birthYear = birthYear,
    gender = gender
)

fun StarHeroEntity.toDomain() = StarHero(
    url = url,
    name = name,
    height = height,
    mass = mass,
    hairColor = hairColor,
    skinColor = skinColor,
    eyeColor = eyeColor,
    homeworld = homeworld,
    birthYear = birthYear,
    gender = gender
)