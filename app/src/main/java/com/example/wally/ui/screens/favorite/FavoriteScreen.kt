package com.example.wally.ui.screens.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wally.ui.OnPictureItemClicked
import com.example.wally.ui.component.EmptyFavorite
import com.example.wally.ui.component.StaggeredList

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: FavoriteViewModel = hiltViewModel()
) {


    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val favorite by viewModel.favorite.collectAsState()

        favorite?.let {
            if (it.isEmpty()) {
                EmptyFavorite(Modifier)
            } else
                StaggeredList(
                    modifier = Modifier,
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