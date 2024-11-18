package com.ibrahimezzat404.wally.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import com.ibrahimezzat404.wally.R

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

object CategoryListScreen : AppDestinations{
    override val title = "CategoryListScreen"
    override val route = "categorylist"
}

object PictureScreen : AppDestinations {
    override val title = "Picture"
    override val route = "picture/{picture}/{featured}"
    const val pictureKey = "picture"
    const val featuredKey = "featured"

}


@Stable
data class DestinationButton(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val destination : AppDestinations
)

val appScreens = listOf(HomeScreen, PictureScreen, CategoriesScreen, SearchScreen, FavoriteScreen, CategoryListScreen)



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