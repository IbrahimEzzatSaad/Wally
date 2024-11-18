package com.ibrahimezzat404.wally.ui.screens.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ibrahimezzat404.wally.ui.component.AppHeader
import com.ibrahimezzat404.wally.utils.OnPictureItemClicked
import com.ibrahimezzat404.wally.ui.component.EmptyFavorite
import com.ibrahimezzat404.wally.ui.component.PictureItem
import com.ibrahimezzat404.wally.ui.component.StaggeredGrid

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: FavoriteViewModel = hiltViewModel()
) {


    val favorite by viewModel.favorite.collectAsState()

    favorite?.let {
        StaggeredGrid(modifier) {
            if (it.isEmpty()) {
                item(span = StaggeredGridItemSpan.FullLine) {
                    AppHeader(navigationIcon = false)
                }

                item(span = StaggeredGridItemSpan.FullLine) {

                    EmptyFavorite(Modifier.padding(20.dp))
                }
            } else {
                item(span = StaggeredGridItemSpan.FullLine) {
                    AppHeader(navigationIcon = false)
                }
                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "Favorites",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 50.dp, start = 10.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "You've marked all of these as favorite!",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(start = 10.dp),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                    )
                }

                itemsIndexed(
                    items = it,
                    key = { index, picture -> picture.id }
                ) { index, pictureItem ->

                    PictureItem(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 5.dp , end = 5.dp, bottom = 10.dp),
                        item = pictureItem,
                        onItemClicked = onPictureItemClicked,
                        height = (pictureItem.height / 12),
                        isFavorite = true,
                        onFavoriteClicked = { id ->
                            viewModel.updateFavorites(id)
                        }
                    )
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(modifier = Modifier.size(65.dp))
                }
            }


        }
    }

}