package com.pobezhkin.starwars_mvi.di

import android.content.Context
import androidx.room.Room
import com.pobezhkin.starwars_mvi.heroes.data.local.StarHeroDao
import com.pobezhkin.starwars_mvi.heroes.data.local.StarWarsDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(context: Context): StarWarsDatabase =
        Room.databaseBuilder(context, StarWarsDatabase::class.java, "starwars.db")
            .build()

    @Provides
    @Singleton
    fun providesStarHeroDao(database: StarWarsDatabase): StarHeroDao = database.starHeroDao()
}
