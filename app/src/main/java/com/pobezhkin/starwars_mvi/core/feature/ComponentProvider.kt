package com.pobezhkin.starwars_mvi.core.feature

interface ComponentProvider {
    fun <T : Component> get(type: Class<out T>): T
}