package com.pobezhkin.starwars_mvi.heroes.data.remote


import com.pobezhkin.starwars_mvi.heroes.data.remote.dto.StarHeroDto
import com.pobezhkin.starwars_mvi.heroes.data.remote.dto.StarHeroesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarApiService {
    @GET("api/people/")
    suspend fun getTopHeroes(@Query("page") page : Int): Response<StarHeroesResponseDto>
    @GET("api/people/{heroId}/")
    suspend fun getHeroById(@Path("heroId") heroId : String): Response<StarHeroDto>
}