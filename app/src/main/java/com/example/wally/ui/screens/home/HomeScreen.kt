package com.example.wally.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.data.api.model.PictureModel
import com.example.wally.ui.component.AppHeader
import com.example.wally.utils.OnFeaturedItemClicked
import com.example.wally.utils.OnPictureItemClicked
import com.example.wally.ui.component.Featured
import com.example.wally.ui.component.NoInternet
import com.example.wally.ui.component.PictureItem
import com.example.wally.ui.component.RefreshButton
import com.example.wally.ui.component.StaggeredGrid
import com.example.wally.ui.theme.VioletsBlue

const val PICTURE_LIST_TEST_TAG = "pictures_list"


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onPictureItemClicked: OnPictureItemClicked,
    onFeaturedItemClicked: OnFeaturedItemClicked,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val featured by viewModel.featured.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val pictures: LazyPagingItems<PictureModel> = viewModel.pictures.collectAsLazyPagingItems()
    val state = rememberPullRefreshState(isLoading, {
        viewModel.retry()
        pictures.retry()
    })
    val favorites by viewModel.favorites.collectAsState()



    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {

        featured?.let {
            StaggeredGrid {

                if (it.isEmpty()) {

                    item(span = StaggeredGridItemSpan.FullLine) {
                        NoInternet(
                            {
                                viewModel.retry()
                                pictures.retry()
                            }, isLoading
                        )
                    }

                } else {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        AppHeader(navigationIcon = false)
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Featured(it, onFeaturedItemClicked)
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = "All",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    items(
                        count = pictures.itemCount,
                        key = pictures.itemKey { it.id }) { index ->
                        pictures[index]?.let { pictureItem ->
                            PictureItem(
                                modifier = Modifier
                                    .fillMaxSize(),
                                item = pictureItem,
                                onItemClicked = onPictureItemClicked,
                                height = (pictureItem.height / 12),
                                isFavorite = favorites.contains(pictureItem.id),
                                onFavoriteClicked = { id ->
                                    viewModel.updateFavorites(id)
                                }
                            )
                        }
                    }

                    pictures.apply {
                        when {
                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                val error = when {
                                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                                    else -> loadState.append as LoadState.Error
                                }


                                item(span = StaggeredGridItemSpan.FullLine) {
                                    RefreshButton { pictures.retry() }
                                }
                            }
                        }
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(modifier = Modifier.size(65.dp))
                    }
                }
            }
        }


        PullRefreshIndicator(
            refreshing = isLoading,
            state = state,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = VioletsBlue
        )
    }
}



