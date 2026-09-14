package com.pobezhkin.starwars_mvi.core.coroutine

import kotlinx.coroutines.Dispatchers

class AppDispatchersImpl : AppDispatchers {
    override val main = Dispatchers.Main.immediate

    override val io = Dispatchers.IO

    override val default = Dispatchers.Default
}
