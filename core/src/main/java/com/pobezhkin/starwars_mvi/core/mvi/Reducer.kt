package com.pobezhkin.starwars_mvi.core.mvi

typealias Reducer<Effect, State> = (effect: Effect, state: State) -> State