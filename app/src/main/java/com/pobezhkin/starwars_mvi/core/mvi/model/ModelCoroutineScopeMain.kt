package com.pobezhkin.starwars_mvi.core.mvi.model

import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ModelCoroutineScopeMain(
    dispatchers: AppDispatchers
) : CoroutineScope {
    override val coroutineContext = SupervisorJob() + dispatchers.main
}
