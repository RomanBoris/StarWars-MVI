package com.pobezhkin.starwars_mvi.heroes.data

import com.pobezhkin.starwars_mvi.core.entity.StarHero
import com.pobezhkin.starwars_mvi.core.log.AppLogger
import com.pobezhkin.starwars_mvi.core.network.NetworkError
import com.pobezhkin.starwars_mvi.core.network.NetworkResult
import com.pobezhkin.starwars_mvi.core.network.networkCall
import com.pobezhkin.starwars_mvi.heroes.api.HeroesRepository
import com.pobezhkin.starwars_mvi.heroes.data.local.StarHeroDao
import com.pobezhkin.starwars_mvi.heroes.data.mappers.toDomain
import com.pobezhkin.starwars_mvi.heroes.data.mappers.toEntity
import com.pobezhkin.starwars_mvi.heroes.data.remote.StarApiService

class HeroesRepositoryImpl(
    private val api: StarApiService,
    private val dao: StarHeroDao,
    private val logger: AppLogger
) : HeroesRepository {

    override suspend fun getAllHeroes(): NetworkResult<List<StarHero>> {
        // Шаг 1. Всегда сначала сеть. networkCall не бросает исключений — только Success/Error.
        val result = networkCall(
            apiCall = { api.getTopHeroes(page = 1) },
            toDomain = { resultsHeroes.map { it.toDomain() } },
            logger = logger,
        )

        return when (result) {
            // Шаг 2. Успех → перезаписываем кэш (clear+insert в одной транзакции) и отдаём свежее.
            is NetworkResult.Success -> {
                dao.replaceAll(result.data.map { it.toEntity() })
                result
            }

            // Шаг 3. Ошибка → в кэш лезем ТОЛЬКО когда сети не было.
            is NetworkResult.Error -> when (result.error) {
                is NetworkError.NoInternet -> {
                    val cached = dao.getAllHeroes().map { it.toDomain() }
                    if (cached.isNotEmpty()) {
                        // Старые данные — но честно помеченные: UI покажет плашку «оффлайн»
                        NetworkResult.Success(cached, fromCache = true)
                    } else {
                        result   // кэш пуст — прикрыться нечем, отдаём исходную ошибку
                    }
                }

                // Сервер ответил ошибкой → маскировать её старыми данными нельзя
                else -> result
            }
        }
    }

    override suspend fun getHeroByUrl(url: String): NetworkResult<StarHero> {
        // SWAPI хочет в пути числовой id: "…/people/5/" → trimEnd('/') → "…/people/5" → "5"
        val heroId = url.trimEnd('/').substringAfterLast('/')

        val result = networkCall(
            apiCall = { api.getHeroById(heroId) },
            toDomain = { this.toDomain() },   // this = StarHeroDto
            logger = logger,
        )

        return when (result) {
            // Деталь кэш НЕ перезаписывает: хозяин кэша — только загрузка списка
            is NetworkResult.Success -> result

            is NetworkResult.Error -> when (result.error) {
                is NetworkError.NoInternet -> {
                    // Герой мог попасть в кэш раньше, когда грузился список (url — @PrimaryKey)
                    val cached = dao.getHeroByUrl(url)?.toDomain()
                    if (cached != null) {
                        NetworkResult.Success(cached, fromCache = true)
                    } else {
                        result
                    }
                }

                else -> result
            }
        }
    }
}
