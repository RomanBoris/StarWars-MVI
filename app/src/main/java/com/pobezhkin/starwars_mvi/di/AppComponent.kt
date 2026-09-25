package com.pobezhkin.starwars_mvi.di

import android.content.Context
import com.pobezhkin.starwars_mvi.core.feature.Component as FeatureComponent
import com.pobezhkin.starwars_mvi.heroes.di.HeroesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        HeroesModule::class,
    ]
)
interface AppComponent {

    val components: Map<Class<*>, @JvmSuppressWildcards FeatureComponent>

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
