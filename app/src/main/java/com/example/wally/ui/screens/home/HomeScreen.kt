package com.example.wally.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.OnFeaturedItemClicked
import com.example.wally.ui.OnPictureItemClicked
import com.example.wally.ui.component.NoInternet
import com.example.wally.ui.component.StaggeredList
import com.example.wally.ui.theme.VioletsBlue

const val PICTURE_LIST_TEST_TAG = "pictures_list"


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onPictureItemClicked: OnPictureItemClicked,
    onFeaturedItemClicked: OnFeaturedItemClicked,
    modifier: Modifier = Modifier,
    viewModel: PictureViewModel = hiltViewModel()
) {
    val featured by viewModel.featured.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val pictures: LazyPagingItems<PicturesItem> = viewModel.pictures.collectAsLazyPagingItems()
    val state = rememberPullRefreshState(isLoading, {
        viewModel.retry()
        pictures.retry()
    })

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(

            ),
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 2500)
            )
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state)
            ) {

                featured?.let {

                    if(it.isEmpty()){
                        NoInternet(modifier,
                            {
                                viewModel.retry()
                                pictures.retry()
                            }, isLoading)
                    }else{
                        StaggeredList(
                            modifier,
                            pictures = pictures,
                            title = "All",
                            onItemClicked = onPictureItemClicked,
                            onFeaturedItemClicked = onFeaturedItemClicked,
                            featured = it,
                            onFavoriteClicked = { item  ->
                                viewModel.updateFavorites(item) }
                        )
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


