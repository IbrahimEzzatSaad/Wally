package com.example.wally.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.wally.data.api.model.PictureModel
import com.example.wally.ui.navigation.PictureScreen.featuredKey
import com.example.wally.ui.navigation.PictureScreen.pictureKey
import com.example.wally.ui.screens.categories.CategoriesScreen
import com.example.wally.ui.screens.categorylist.CategoryListScreen
import com.example.wally.ui.screens.categorylist.CategoryViewModel
import com.example.wally.ui.screens.favorite.FavoriteScreen
import com.example.wally.ui.screens.home.HomeScreen
import com.example.wally.ui.screens.picture.PictureScreen
import com.example.wally.ui.screens.search.SearchScreen
import com.example.wally.ui.screens.splash.SplashScreen
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {


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


