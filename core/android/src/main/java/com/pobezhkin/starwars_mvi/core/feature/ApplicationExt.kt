package com.pobezhkin.starwars_mvi.core.feature

import android.app.Application

fun <T : Component> Application.getComponent(type: Class<out T>): T =
    if (this is ComponentProvider) get(type)
    else error("Application is not implements ComponentProvider interface")
