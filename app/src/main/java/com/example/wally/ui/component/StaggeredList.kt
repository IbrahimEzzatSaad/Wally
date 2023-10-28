package com.example.wally.ui.component

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.screens.OnFavoriteClicked
import com.example.wally.ui.screens.OnFeaturedItemClicked
import com.example.wally.ui.screens.OnPictureItemClicked
import com.example.wally.ui.screens.PICTURE_LIST_TEST_TAG

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaggeredList(
    modifier: Modifier,
    pictures: LazyPagingItems<PicturesItem>? = null,
    featured: List<PicturesItem> = emptyList(),
    favorite: List<PicturesItem>? = null,
    title: String? = null,
    supTitle: String? = null,
    onFeaturedItemClicked: OnFeaturedItemClicked? = null,
    onItemClicked: OnPictureItemClicked,
    onFavoriteClicked: OnFavoriteClicked
) {
    val listState: LazyStaggeredGridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .testTag(PICTURE_LIST_TEST_TAG),
        state = listState,
        columns = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2)
    ) {
        if (featured.size > 3 && onFeaturedItemClicked != null) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Featured(featured, onFeaturedItemClicked)
            }
        }


        title?.let {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(3.dp),
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.size(5.dp))
            }
        }

        supTitle?.let {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(3.dp),
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.size(5.dp))
            }
        }

        pictures?.let {
            items(pictures.itemCount,
                key = pictures.itemKey { it.id },
                contentType = pictures.itemContentType { it }) { index ->
                pictures[index]?.let { pictureItem ->
                    PictureItem(
                        modifier = Modifier
                            .fillMaxSize(),
                        item = pictureItem,
                        onItemClicked = onItemClicked,
                        height = (pictureItem.height / 12),
                        onFavoriteClicked = onFavoriteClicked
                    )
                }
            }
        }

        favorite?.let {
            items(favorite.size) { index ->
                favorite[index].let { pictureItem ->
                    PictureItem(
                        modifier = Modifier
                            .fillMaxSize(),
                        item = pictureItem,
                        onItemClicked = onItemClicked,
                        height = (pictureItem.height / 12),
                        onFavoriteClicked = onFavoriteClicked,
                    )
                }
            }
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            Spacer(modifier = Modifier.size(60.dp))
        }
    }
}
