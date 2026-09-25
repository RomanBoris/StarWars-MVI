package com.pobezhkin.starwars_mvi.heroes.api

import com.pobezhkin.starwars_mvi.core.feature.Component

// То, что фича heroes отдаёт наружу — Application достаёт этот компонент по классу
// (getComponent(HeroesApi::class.java)) и берёт из него фабрику экрана.
interface HeroesApi : Component {
    val heroesFactory: HeroesScreenComponent.Factory
}
