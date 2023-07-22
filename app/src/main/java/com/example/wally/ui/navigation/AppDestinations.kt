package com.example.wally.ui.navigation

interface AppDestinations {
    val title: String
    val route: String
}

object HomeScreen : AppDestinations {
    override val title = "HomeScreen"
    override val route = "pictures"
}

object SplashScreen : AppDestinations {
    override val title = "SplashScreen"
    override val route = "splash"
}

object PictureDetails : AppDestinations {
    override val title = "Details"
    override val route = "picture"
    const val articleKey = "picture_argument"
}

