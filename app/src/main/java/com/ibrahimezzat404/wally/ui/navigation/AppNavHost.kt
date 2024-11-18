package com.ibrahimezzat404.wally.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.ui.navigation.PictureScreen.featuredKey
import com.ibrahimezzat404.wally.ui.navigation.PictureScreen.pictureKey
import com.ibrahimezzat404.wally.ui.screens.categories.CategoriesScreen
import com.ibrahimezzat404.wally.ui.screens.categorylist.CategoryListScreen
import com.ibrahimezzat404.wally.ui.screens.categorylist.CategoryViewModel
import com.ibrahimezzat404.wally.ui.screens.favorite.FavoriteScreen
import com.ibrahimezzat404.wally.ui.screens.home.HomeScreen
import com.ibrahimezzat404.wally.ui.screens.picture.PictureScreen
import com.ibrahimezzat404.wally.ui.screens.search.SearchScreen
import com.ibrahimezzat404.wally.ui.screens.splash.SplashScreen
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val onBack = {
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.popBackStack()
        }
    }

    NavHost(
        navController = navController,
        startDestination = SplashScreen.route,
    ) {
        composable(route = HomeScreen.route) {

            HomeScreen(
                modifier = modifier,
                onPictureItemClicked = { picture ->
                    navController.navigateToPicture(picture)
                },
                onFeaturedItemClicked = { featured ->
                    navController.navigateToPicture(featured, true)
                }
            )
        }

        composable(route = SplashScreen.route) {
            SplashScreen(
                onTimeEnd = {
                    navController.navigate(route = HomeScreen.route) {
                        popUpTo(SplashScreen.route) { inclusive = true }
                    }
                })
        }

        composable(route = FavoriteScreen.route) {

            FavoriteScreen(
                modifier = modifier,
                onPictureItemClicked = { picture ->

                    navController.navigateToPicture(picture)
                })
        }

        composable(route = SearchScreen.route) {
            SearchScreen(
                modifier = modifier,
                onPictureItemClicked = { picture ->
                    navController.navigateToPicture(picture)
                })
        }

        composable(route = CategoriesScreen.route) {

            val viewModel: CategoryViewModel = hiltViewModel(it)

            CategoriesScreen(
                modifier = modifier,
                onCategoryClicked = { id ->
                    viewModel.startSubscribe(id)
                    navController.navigate(route = CategoryListScreen.route)
                })
        }

        composable(route = CategoryListScreen.route) {
            val backStackEntry = remember(it) {
                navController.getBackStackEntry(CategoriesScreen.route)
            }

            val viewModel: CategoryViewModel = hiltViewModel(backStackEntry)

            CategoryListScreen(
                modifier = modifier,
                viewModel = viewModel,
                onBack = onBack,
                onPictureItemClicked = { picture ->
                    navController.navigateToPicture(picture)
                })
        }

        composable(route = PictureScreen.route) {
            val picture = it.arguments?.getString(pictureKey)
            val featured = it.arguments?.getString(featuredKey)

            if (picture != null && featured != null) {
                val pictureModel = Gson().fromJson(
                    picture,
                    PictureModel::class.java
                )
                PictureScreen(
                    modifier,
                    pictureModel,
                    featured.toBoolean(),
                    { navController.popBackStack() })
            }
        }
    }
}

private fun NavHostController.navigateToPicture(picture: PictureModel, featured: Boolean = false) {
    val json = Gson().toJson(picture)
    val utf8Bytes = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
    this.navigate(
        route = PictureScreen.route.replace("{$pictureKey}", utf8Bytes)
            .replace("{$featuredKey}", featured.toString())
    )
}


