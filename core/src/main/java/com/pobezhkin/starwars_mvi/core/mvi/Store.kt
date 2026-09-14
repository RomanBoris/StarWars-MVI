package com.pobezhkin.starwars_mvi.core.mvi

import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchers
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.core.mvi.model.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

data class StoreData<Effect, State>(
    val middleware: Set<Middleware<Effect, State>>,
    val initState: State,
    val initEffects: List<Effect>,
    val logger: AppLogger,
    val reducer: Reducer<Effect, State>,
    val dispatchers: AppDispatchers,
    val retry: Int = 3,
    val errorHandler: suspend FlowCollector<Effect>.(Throwable) -> Unit = {}

)

class Store<Effect, State>(
    private val coroutineScope: CoroutineScope,
    private val data: StoreData<Effect, State>
) {
    private val state = MutableStateFlow(data.initState)
    private val effects = MutableSharedFlow<Effect>()
    private val internalEffects = MutableSharedFlow<Effect>()

    private val mutex =
        Mutex() // Гарантирует, что редюсер обрабатывает эффекты строго по одному, без гонок

    val stateFlow: StateFlow<State> = state.asStateFlow()

    init {
        val initEffects = data.initEffects.asFlow()
        val middleFlow = data.middleware.map {
            apply(it, initEffects, AtomicInteger(0))
        }.merge()
            .onEach { coroutineScope.launch { internalEffects.emit(it) } }

        listOf(initEffects, effects, middleFlow).merge()
            .onEach(::apply)
            .launchIn(coroutineScope)
    }

    fun effect(effect: Effect) {
        coroutineScope.launch {
            effects.emit(effect)
        }
    }

    private suspend fun apply(effect: Effect) = mutex.withLock {
        state.emit(data.reducer(effect, state.value))
    }

    private fun apply(
        middleware: Middleware<Effect, State>,
        initEffect: Flow<Effect>,
        retryCount: AtomicInteger,
    ): Flow<Effect> = middleware(
        listOf(
            initEffect,
            effects.asSharedFlow(),
            internalEffects.asSharedFlow()
        ).merge(),
        stateFlow,
    ).catch { e ->
        data.logger.logWarning(TAG, "Exception in ${middleware.javaClass.name}", e)
        data.logger.logError(e)
        data.errorHandler(this, e)

        if (data.retry < 0 || retryCount.incrementAndGet() <= data.retry) {
            emitAll(apply(middleware, emptyFlow(), retryCount))
        }

    }

    private companion object {
        const val TAG = "Store"
    }


}

fun <State, Effect> Model<State, Effect>.makeStore(data: StoreData<Effect, State>) =
    Store(this, data)