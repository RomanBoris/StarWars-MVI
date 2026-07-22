package com.pobezhkin.starwars_mvi.core.network

sealed class NetworkError {
    data object NoInternet : NetworkError()
    data class Http(val code: Int) : NetworkError()

    data object Unknown  : NetworkError()
}