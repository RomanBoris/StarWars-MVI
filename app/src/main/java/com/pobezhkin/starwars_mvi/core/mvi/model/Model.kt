package com.pobezhkin.starwars_mvi.core.mvi.model

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface Model<State, Effect>: CoroutineScope {

    val stateFlow: StateFlow<State>
    fun effect(effect: Effect)
}