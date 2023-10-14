package com.example.wally.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Stable
import com.example.wally.R

interface AppDestinations {
    val title: String
    val route: String
}

object HomeScreen : AppDestinations {
    override val title = "HomeScreen"
    override val route = "home"
}

object SearchScreen : AppDestinations {
    override val title = "SearchScreen"
    override val route = "search"
}

object FavoriteScreen : AppDestinations {
    override val title = "FavoriteScreen"
    override val route = "favorite"
}

object CategoriesScreen : AppDestinations {
    override val title = "CategoriesScreen"
    override val route = "categories"
}

object SplashScreen : AppDestinations {
    override val title = "SplashScreen"
    override val route = "splash"
}

object PictureDetails : AppDestinations {
    override val title = "Details"
    override val route = "picture"
    const val pictureKey = "picture_argument"
}


@Stable
data class DestinationButton(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val destination : AppDestinations
)

val appScreens = listOf(HomeScreen, PictureDetails, CategoriesScreen, SearchScreen, FavoriteScreen)

val dropletButtons = listOf(
    DestinationButton(
        icon = R.drawable.home,
        isSelected = false,
        description = R.string.home,
        destination = HomeScreen
    ),
    DestinationButton(
        icon = R.drawable.categories,
        isSelected = false,
        description = R.string.categories,
        destination = CategoriesScreen
    ),
    DestinationButton(
        icon = R.drawable.search,
        isSelected = false,
        description = R.string.search,
        destination = SearchScreen
    ),
    DestinationButton(
        icon = R.drawable.heart,
        isSelected = false,
        description = R.string.favorite,
        destination = FavoriteScreen
    ),
)