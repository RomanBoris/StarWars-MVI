package com.pobezhkin.starwars_mvi.core.network

sealed interface NetworkResult<out D> {
    data class Success<out D>(
        val data: D,
        val fromCache: Boolean = false
    ): NetworkResult<D>

    data class Error(val error: NetworkError): NetworkResult<Nothing>
}