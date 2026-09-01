package com.pobezhkin.starwars_mvi.heroes.submappers

import android.content.Context
import com.pobezhkin.starwars_mvi.R
import com.pobezhkin.starwars_mvi.core.network.NetworkError

class NetworkErrorTextMapper(
    private val context: Context
) : (NetworkError) -> String {
    override fun invoke(error: NetworkError): String = when (error) {

        is NetworkError.NoInternet -> context.getString(R.string.error_no_internet)
        is NetworkError.Http -> context.getString(R.string.error_http, error.code)
        is NetworkError.Unknown -> context.getString(R.string.error_unknown)
    }
}