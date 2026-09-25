package com.pobezhkin.starwars_mvi.core.feature

interface Container <T: Component, D: Dependencies> {
    fun init(dependencies: D)
    fun provide(): T
    fun reset()
}