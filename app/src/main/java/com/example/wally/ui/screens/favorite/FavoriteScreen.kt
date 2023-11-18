package com.example.wally.ui.screens.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wally.utils.OnPictureItemClicked
import com.example.wally.ui.component.EmptyFavorite
import com.example.wally.ui.component.PictureItem
import com.example.wally.ui.component.StaggeredGrid

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: FavoriteViewModel = hiltViewModel()
) {


        val favorite by viewModel.favorite.collectAsState()

        favorite?.let {
            StaggeredGrid(modifier){
                if (it.isEmpty()) {
                    item(span = StaggeredGridItemSpan.FullLine) {

                        EmptyFavorite(Modifier)
                    }
                } else {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = "Favorites",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 50.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = "You've marked all of these as favorite!",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }

                    items(it.size) { index ->
                        it[index].let { pictureItem ->
                            PictureItem(
                                modifier = Modifier
                                    .fillMaxSize(),
                                item = pictureItem,
                                onItemClicked = onPictureItemClicked,
                                height = (pictureItem.height / 12),
                                onFavoriteClicked = { id ->
                                    viewModel.updateFavorites(id)
                                }
                            )
                        }
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(modifier = Modifier.size(65.dp))
                    }
                }


        }
    }

}