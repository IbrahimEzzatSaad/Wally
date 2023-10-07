package com.example.wally.ui.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.component.Featured
import com.example.wally.ui.component.PictureItem
import com.example.wally.R
import com.example.wally.ui.component.NoInternet
import com.example.wally.ui.component.StaggeredList
import com.example.wally.ui.theme.Purple40
import com.example.wally.ui.theme.VioletsBlue
import okhttp3.internal.checkDuration

typealias OnPictureItemClicked = (PicturesItem) -> Unit
typealias OnRetryClicked = () -> Unit
typealias OnFeaturedItemClicked = (List<PicturesItem>, Int) -> Unit

const val PICTURE_LIST_TEST_TAG = "pictures_list"


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onPictureItemClicked: OnPictureItemClicked,
    onFeaturedItemClicked: OnFeaturedItemClicked,
    modifier: Modifier = Modifier,
    viewModel: PictureViewModel = hiltViewModel()
) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val featured by viewModel.featured.collectAsState()
        val uiState by viewModel.uiState.collectAsState()
        val pictures: LazyPagingItems<PicturesItem> = viewModel.pictures.collectAsLazyPagingItems()
        val state = rememberPullRefreshState(uiState.isLoading, {
            viewModel.retry()
            pictures.retry()
        })


        if(uiState.errorMessage.isNotEmpty() && !uiState.isLoading){
            Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT)
                .show()
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn(

            ),
            exit = fadeOut(
                // Overwrites the default animation with tween
                animationSpec = tween(durationMillis = 250)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state)
            ) {
                featured?.let {
                    if (it.size > 3) {
                        StaggeredList(
                            modifier = modifier,
                            pictures = pictures,
                            onItemClicked = onPictureItemClicked,
                            featured = it,
                            onFeaturedItemClicked = onFeaturedItemClicked
                        )

                    } else if (it.isEmpty()){
                        NoInternet({
                            viewModel.retry()
                            pictures.retry()
                        }, uiState.isLoading)
                    }
                }

                PullRefreshIndicator(
                    refreshing = uiState.isLoading,
                    state = state,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = VioletsBlue
                )
            }
        }
    }
}


