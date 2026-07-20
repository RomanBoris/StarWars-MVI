package com.pobezhkin.starwars_mvi.core.log

import android.util.Log


interface AppLogger {
    fun logDebug(tag: String, message: String)
    fun logWarning(tag: String, message: String, e: Throwable? = null)
    fun logError(e: Throwable)
}

class AndroidLogger: AppLogger{
    override fun logDebug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun logWarning(tag: String, message: String, e: Throwable?) {
        Log.w(tag, message, e)
    }

    override fun logError(e: Throwable) {
        Log.e("StarWarsMVI", e.message.orEmpty(), e)
    }
}