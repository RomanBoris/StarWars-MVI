package com.pobezhkin.starwars_mvi.core.coroutine


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


fun <T, R> StateFlow<T>.map(
    coroutineScope: CoroutineScope,
    mapper: (value: T) -> R,

): StateFlow<R> = map{mapper(it)}
    .stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        mapper(value)
    )