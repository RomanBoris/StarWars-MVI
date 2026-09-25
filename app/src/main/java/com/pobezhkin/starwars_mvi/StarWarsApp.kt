package com.pobezhkin.starwars_mvi

import android.app.Application
import com.pobezhkin.starwars_mvi.core.feature.Component
import com.pobezhkin.starwars_mvi.core.feature.ComponentProvider
import com.pobezhkin.starwars_mvi.di.AppComponent
import com.pobezhkin.starwars_mvi.di.DaggerAppComponent

class StarWarsApp : Application(), ComponentProvider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Component> get(type: Class<out T>): T =
        appComponent.components[type] as? T
            ?: error("Component ${type.simpleName} is not registered in AppComponent")
}
