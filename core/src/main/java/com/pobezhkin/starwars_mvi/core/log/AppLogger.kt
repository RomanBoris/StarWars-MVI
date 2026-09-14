package com.pobezhkin.starwars_mvi.core.log

interface AppLogger {
    fun logDebug(tag: String, message: String)
    fun logWarning(tag: String, message: String, e: Throwable? = null)
    fun logError(e: Throwable)
}