package com.example.wally.ui.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.net.URLDecoder
import java.net.URLEncoder

@OptIn(ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ConnectivityStatus()

        NavHost(
            modifier = Modifier.padding(start = 15.dp,
                end = 15.dp),
            navController = navController,
            startDestination = SplashScreen.route,
        ) {
            composable(route = HomeScreen.route) {
                HomeScreen(
                    onPictureItemClicked = {
                        /*navController.navigateToSinglePicture(it)*/
                    },
                    onFeaturedItemClicked = { featured, index ->
                        /*navController.navigateToSinglePicture(it)*/
                    }
                )
            }

            composable(route = SplashScreen.route) {
                SplashScreen( onTimeEnd = {
                    navController.navigate(route = HomeScreen.route)
                })
            }

            composable(route = FavoriteScreen.route) {
                FavoriteScreen(onPictureItemClicked = {
                    /*navController.navigateToSinglePicture(it)*/
                })
            }

            composable(route = SearchScreen.route) {
                SearchScreen(onPictureItemClicked = {
                    /*navController.navigateToSinglePicture(it)*/
                })
            }

            composable(route = CategoriesScreen.route) {

                val viewModel: CategoryViewModel = hiltViewModel(it)

                CategoriesScreen( onCategoryClicked = { id ->
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
                    viewModel = viewModel,
                    onPictureItemClicked = {
                        val json = Gson().toJson(it)

                        // Encode the JSON string to UTF-8 byte array
                        val utf8Bytes = URLEncoder.encode(json, "utf-8")

                        navController.navigate(route = PictureScreen.route + "/$utf8Bytes")
                    })
            }

            composable(route = PictureScreen.route + "/{picture}") {
                val argument = it.arguments?.getString("picture")


                if (argument != null) {
                    val picture = Gson().fromJson(URLDecoder.decode(argument, "utf-8"), PicturesItem::class.java)
                    PictureScreen(picture)

                }

            }
        }
    }
}


