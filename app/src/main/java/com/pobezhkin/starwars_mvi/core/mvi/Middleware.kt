package com.pobezhkin.starwars_mvi.core.mvi

import kotlinx.coroutines.flow.Flow

typealias Middleware<Effect, State> = (effect: Flow<Effect>, state: Flow<State>) -> Flow<Effect>