package com.pobezhkin.starwars_mvi.heroes.api

// Контракт сабкомпонента одного экрана (скоуп — время жизни ViewModel).
// heroes/api — чистый Kotlin-модуль без Android, поэтому viewModel здесь — не androidx.lifecycle.ViewModel,
// а собственный маркер. Настоящий HeroesViewModel (в :heroes:impl) реализует оба типа сразу.
interface HeroesScreenComponent {
    val viewModel: ViewModel

    interface Factory {
        fun create(): HeroesScreenComponent
    }

    interface ViewModel
}
