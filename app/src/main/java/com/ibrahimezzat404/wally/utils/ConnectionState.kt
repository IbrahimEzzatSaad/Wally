package com.ibrahimezzat404.wally.utils

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}