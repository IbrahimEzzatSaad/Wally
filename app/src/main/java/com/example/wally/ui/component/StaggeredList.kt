package com.example.wally.ui.component

import android.content.res.Configuration
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
import com.example.wally.ui.screens.OnFeaturedItemClicked
import com.example.wally.ui.screens.OnPictureItemClicked
import com.example.wally.ui.screens.PICTURE_LIST_TEST_TAG

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaggeredList(
    modifier: Modifier,
    featured: List<PicturesItem> = emptyList(),
    onFeaturedItemClicked: OnFeaturedItemClicked,
    pictures: LazyPagingItems<PicturesItem>,
    onItemClicked: OnPictureItemClicked
) {
    val listState: LazyStaggeredGridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .testTag(PICTURE_LIST_TEST_TAG)
            .padding(10.dp),
        state = listState,
        columns = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2)
    ) {
        if (featured.size > 3) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Featured(featured, onFeaturedItemClicked)
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = "All",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.size(5.dp))
            }
        }

        items(pictures.itemCount,
            key = pictures.itemKey { it.id },
            contentType = pictures.itemContentType { it }) { index ->
            pictures[index]?.let {
                PictureItem(
                    modifier = Modifier
                        .fillMaxSize(),
                    item = it,
                    onItemClicked = onItemClicked,
                    height = (it.height / 12)
                )
            }
        }
    }
}