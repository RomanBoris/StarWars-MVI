package com.pobezhkin.starwars_mvi.heroes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StarHeroEntity::class], version = 1)
abstract class StarWarsDatabase: RoomDatabase() {
    abstract fun starHeroDao(): StarHeroDao
}