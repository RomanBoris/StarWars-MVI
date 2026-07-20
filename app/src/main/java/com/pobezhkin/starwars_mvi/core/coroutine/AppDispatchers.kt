package com.pobezhkin.starwars_mvi.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface AppDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
}

class AppDispatchersImpl : AppDispatchers {
    override val main = Dispatchers.Main.immediate

    override val io = Dispatchers.IO

    override val default = Dispatchers.Default
}
