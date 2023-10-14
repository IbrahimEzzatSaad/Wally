package com.example.wally.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wally.ui.component.EmptyFavorite
import com.example.wally.ui.component.StaggeredList
import com.example.wally.ui.theme.VioletsBlue

@Composable
fun FavoriteScreen(
    modifier: Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: FavoriteViewModel = hiltViewModel()
) {


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val favorite by viewModel.favorite.collectAsState()

        favorite?.let {
            if (it.isEmpty()) {
                EmptyFavorite()
            } else
                StaggeredList(
                    modifier = modifier,
                    favorite = favorite,
                    title = "Favorites",
                    supTitle = "You've marked all of these as favorite!",
                    onItemClicked = onPictureItemClicked,
                    onFavoriteClicked = { id ->
                        viewModel.updateFavorites(id)
                    }
                )
        }

    }

}