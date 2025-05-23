package com.ibrahimezzat404.wally.data.api.interceptors

import com.ibrahimezzat404.wally.domain.usecases.NetworkUnavailableException
import com.ibrahimezzat404.wally.data.api.ConnectionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(private val connectionManager: ConnectionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (connectionManager.isConnected()) {
            chain.proceed(chain.request())
        } else {
            throw NetworkUnavailableException()
        }
    }
}