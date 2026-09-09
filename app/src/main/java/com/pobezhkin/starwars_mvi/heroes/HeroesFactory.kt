package com.pobezhkin.starwars_mvi.heroes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.pobezhkin.starwars_mvi.core.coroutine.AppDispatchersImpl
import com.pobezhkin.starwars_mvi.core.log.AndroidLogger
import com.pobezhkin.starwars_mvi.core.mvi.Middleware
import com.pobezhkin.starwars_mvi.heroes.data.HeroesRepositoryImpl
import com.pobezhkin.starwars_mvi.heroes.data.local.StarWarsDatabase
import com.pobezhkin.starwars_mvi.heroes.data.remote.StarApiService
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesEffect
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesModel
import com.pobezhkin.starwars_mvi.heroes.mvi.HeroesState
import com.pobezhkin.starwars_mvi.heroes.mvi.middleware.LoadHeroDetailsMiddleware
import com.pobezhkin.starwars_mvi.heroes.mvi.middleware.LoadHeroesMiddleware
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ВРЕМЕННАЯ фабрика урока 12 — собирает весь граф фичи руками, без Dagger.
// В уроке 13 этот файл целиком заменится на Dagger (core/feature, Dependencies, Component),
// а вызывающий код (HeroesFragment) поменяет только ОДНУ строку — откуда берётся HeroesFactory.
class HeroesFactory(
    // Обязательно applicationContext — см. ниже, кто его держит и как долго.
    private val context: Context,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dispatchers = AppDispatchersImpl()
        val logger = AndroidLogger()

        // --- сеть: тот же стек, что был в старом StarWarsApp-master/di/NetworkModule ---
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://swapi.dev/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(StarApiService::class.java)

        // --- база: та же, что была в старом DatabaseModule ---
        val database = Room.databaseBuilder(context, StarWarsDatabase::class.java, "starwars.db")
            .build()
        val dao = database.starHeroDao()

        val repository = HeroesRepositoryImpl(api, dao, logger)

        // Тот самый Set<Middleware<...>>, который ждёт конструктор HeroesModel (урок 10).
        val middleware: Set<Middleware<HeroesEffect, HeroesState>> = setOf(
            LoadHeroesMiddleware(repository),
            LoadHeroDetailsMiddleware(repository),
        )

        val model = HeroesModel(
            middleware = middleware,
            dispatchers = dispatchers,
            logger = logger,
        )

        val viewModel = HeroesViewModel(
            interactor = model,
            stateToViewStateMapper = HeroesStateToViewStateMapper(context),
        )

        // Фабрика у нас на одну ViewModel — modelClass не проверяем, всегда отдаём HeroesViewModel.
        return viewModel as T
    }
}