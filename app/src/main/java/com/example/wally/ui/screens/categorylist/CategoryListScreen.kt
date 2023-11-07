package com.example.wally.ui.screens.categorylist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.OnPictureItemClicked
import com.example.wally.ui.component.PictureItem
import com.example.wally.ui.component.RefreshButton
import com.example.wally.ui.component.StaggeredGrid
import com.example.wally.ui.model.categories
import com.example.wally.ui.theme.VioletsBlue

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CategoryListScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val categoryList: LazyPagingItems<PicturesItem> = viewModel.category.collectAsLazyPagingItems()
    val id = viewModel.id.collectAsState().value
    val category = categories.find { it.slug == id }
    val isLoading by viewModel.isLoading.collectAsState()
    val state = rememberPullRefreshState(isLoading, {
        viewModel.loadingUpdate()
        categoryList.refresh()
    })

    AnimatedVisibility(
        visible = true,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state)
            ) {


                StaggeredGrid {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = category?.title ?: "Category",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier,
                            color = Color.White
                        )
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text(
                            text = category?.subTitle ?: "",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier,
                            color = Color.White.copy(alpha = 0.7f)
                        )
                    }


                    items(categoryList.itemCount,
                        key = categoryList.itemKey { it.id },
                        contentType = categoryList.itemContentType { it }) { index ->
                        categoryList[index]?.let { pictureItem ->
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


                    categoryList.apply {
                        when {
                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                val error = when {
                                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                                    else -> loadState.append as LoadState.Error
                                }


                                item(span = StaggeredGridItemSpan.FullLine) {
                                    RefreshButton { categoryList.retry() }
                                }
                            }
                        }
                    }

                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(modifier = Modifier.size(65.dp))
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
    }


}