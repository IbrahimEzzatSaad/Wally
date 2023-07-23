package com.example.wally.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wally.ui.screens.HomeScreen
import com.example.wally.ui.screens.SplashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
        modifier = modifier
    ) {
        composable(route = HomeScreen.route) {
            HomeScreen(
                modifier = modifier,
                onPictureItemClicked = {
                    /*navController.navigateToSingleArticle(it)*/
                }
            )
        }

        composable(route = SplashScreen.route) {
            SplashScreen(modifier = modifier, onTimeEnd = {
                navController.navigate(route = HomeScreen.route)
            })
        }

        /*composable(
            route = ArticleDetails.route
        ) { navBackStackEntry ->
            val article =
                if (Build.VERSION.SDK_INT >= 33) {
                    navBackStackEntry.arguments?.getSerializable(articleKey, PictureInfo::class.java)
                } else {
                    navBackStackEntry.arguments?.getSerializable(articleKey) as PictureInfo?
                }
            article?.let {
                val viewModel = hiltViewModel<PictureViewModel>()
                viewModel.getArticleById(it.id)
                ArticleDetailsScreen(modifier= modifier.padding(16.dp),viewModel = viewModel)
            }
        }
        */
    }
}

/*
private fun NavHostController.navigateToSingleArticle(picture: PicturesItem) {
    this.navigatee(route = PictureDetails.route, Bundle().apply {
        this.putSerializable(articleKey, articleUI)
    })
}*/

/*fun NavHostController.navigatee(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {

    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}*/

val appScreens = listOf(HomeScreen, PictureDetails)
