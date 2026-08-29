package com.pobezhkin.starwars_mvi.heroes.mvi

import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.core.mvi.Middleware
import com.pobezhkin.starwars_mvi.core.mvi.StoreData
import com.pobezhkin.starwars_mvi.core.mvi.makeStore
import com.pobezhkin.starwars_mvi.core.mvi.model.ModelCoroutineScopeMain
import kotlinx.coroutines.CoroutineScope

class HeroesModel(
    middleware: Set<Middleware<HeroesEffect, HeroesState>>,
    dispatchers: AppDispatchers,
    logger: AppLogger
): HeroesInteractor, CoroutineScope by ModelCoroutineScopeMain(dispatchers) {
    private val store = makeStore(
        StoreData(
            middleware = middleware,
            initState = HeroesReducer.initialState(),
            initEffects = listOf(HeroesEffect.List.Load),
            logger = logger,
            reducer = HeroesReducer(),
            dispatchers = dispatchers

        )
    )
    override val stateFlow = store.stateFlow
    override fun effect(effect: HeroesEffect) = store.effect(effect)
}