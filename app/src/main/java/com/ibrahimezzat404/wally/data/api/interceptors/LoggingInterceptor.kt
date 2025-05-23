package com.ibrahimezzat404.wally.data.api.interceptors

import com.ibrahimezzat404.wally.utils.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.i(message)
    }
}