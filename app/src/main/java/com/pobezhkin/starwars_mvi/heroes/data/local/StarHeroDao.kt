package com.pobezhkin.starwars_mvi.heroes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface StarHeroDao {
    @Query("SELECT * FROM starHero")
    suspend fun getAllHeroes(): List<StarHeroEntity>
    @Query("SELECT * FROM starHero WHERE url = :url")
    suspend fun getHeroByUrl(url: String): StarHeroEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroes(heroes: List<StarHeroEntity>)

    @Query("DELETE FROM starHero")
    suspend fun clearHeroes()

    @Transaction
    suspend fun replaceAll(heroes: List<StarHeroEntity>){
        clearHeroes()
        insertHeroes(heroes)
    }

}