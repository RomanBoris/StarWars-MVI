package com.pobezhkin.starwars_mvi.heroes.di

import com.pobezhkin.starwars_mvi.core.di.scope.FeatureScope
import com.pobezhkin.starwars_mvi.core.di.scope.ViewModelScope
import com.pobezhkin.starwars_mvi.heroes.HeroesViewModel
import com.pobezhkin.starwars_mvi.heroes.api.HeroesScreenComponent
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module(subcomponents = [HeroesViewModule.HeroesSubComponent::class])
class HeroesViewModule {

    @ViewModelScope
    @Subcomponent(modules = [HeroesInteractorModule::class])
    interface HeroesSubComponent : HeroesScreenComponent {
        override val viewModel: HeroesViewModel

        @Subcomponent.Factory
        interface Factory : HeroesScreenComponent.Factory {
            override fun create(): HeroesScreenComponent
        }
    }

    @Provides
    @FeatureScope
    fun provideSubComponentFactory(
        factory: HeroesSubComponent.Factory,
    ): HeroesScreenComponent.Factory = factory
}
