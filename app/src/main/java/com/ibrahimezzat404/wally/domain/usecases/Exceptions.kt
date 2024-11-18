package com.ibrahimezzat404.wally.domain.usecases

import java.io.IOException

class NoMorePicturesException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

class NetworkException(message: String): Exception(message)