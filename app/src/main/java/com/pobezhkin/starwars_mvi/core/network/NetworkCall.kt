package com.pobezhkin.starwars_mvi.core.network

import com.pobezhkin.starwars_mvi.core.log.AppLogger
import retrofit2.Response

import java.io.IOException

suspend fun <T, D> networkCall(
    apiCall: suspend () -> Response<T>,
    toDomain: T.() -> D,
    logger: AppLogger,
): NetworkResult<D> = try {
    val response = apiCall()
    val body = response.body()

    if(response.isSuccessful && body != null){
        NetworkResult.Success(body.toDomain())
    }else{
        logger.logWarning("networkCall", "HTTP ${response.code()}")
        NetworkResult.Error(NetworkError.Http(response.code()))
    }

}catch (e: IOException) {                // нет интернета / таймаут
    logger.logError(e)
    NetworkResult.Error(NetworkError.NoInternet)
} catch (e: Throwable) {                  // ошибка парсинга и прочее
    logger.logError(e)
    NetworkResult.Error(NetworkError.Unknown)
}