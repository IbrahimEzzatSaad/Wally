package com.example.wally.ui.screens.categorylist

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
import com.example.wally.ui.OnPictureItemClicked
import com.example.wally.ui.component.StaggeredList
import com.example.wally.ui.model.categories
import com.example.wally.ui.theme.VioletsBlue

@OptIn(ExperimentalMaterialApi::class)
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

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        Box(modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state)
        ) {


            StaggeredList(
                modifier = modifier,
                pictures = categoryList,
                onItemClicked = onPictureItemClicked,
                title = category?.title,
                supTitle = category?.subTitle,
                onFavoriteClicked = { id ->
                    viewModel.updateFavorites(id)
                }
            )

            PullRefreshIndicator(
                refreshing = isLoading,
                state = state,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = VioletsBlue
            )
        }
    }


}