package com.example.wally.ui.screens.search

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.R
import com.example.wally.ui.OnPictureItemClicked
import com.example.wally.ui.component.PictureItem
import com.example.wally.ui.component.RefreshButton
import com.example.wally.ui.component.SearchField
import com.example.wally.ui.component.StaggeredGrid


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResult = viewModel.search.collectAsLazyPagingItems()
    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        StaggeredGrid(modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = null
        ) { hideKeyboard = true }) {
            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = "Search",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier,
                    color = Color.White
                )
            }


            item(span = StaggeredGridItemSpan.FullLine) {
                Text(
                    text = "searching through hundreds of photos will be so much easier now.",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                SearchField(
                    onValueChange = { viewModel.search(it) },
                    hideKeyboard = hideKeyboard,
                    onFocusClear = { hideKeyboard = false }
                )
            }

            if (searchResult.itemCount > 0) {

                items(searchResult.itemCount,
                    key = searchResult.itemKey { it.id },
                    contentType = searchResult.itemContentType { it }) { index ->
                    searchResult[index]?.let { pictureItem ->
                        PictureItem(
                            modifier = Modifier
                                .fillMaxSize(),
                            item = pictureItem,
                            onItemClicked = onPictureItemClicked,
                            height = (pictureItem.height / 12),
                            onFavoriteClicked = { id ->
                                viewModel.updateFavorites(id)
                                hideKeyboard = true
                            }
                        )
                    }
                }

                searchResult.apply {
                    when {
                        loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                            val error = when {
                                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                                else -> loadState.append as LoadState.Error
                            }


                            item(span = StaggeredGridItemSpan.FullLine) {
                                RefreshButton { searchResult.retry() }
                            }
                        }
                    }
                }

                item(span = StaggeredGridItemSpan.FullLine) {
                    Spacer(modifier = Modifier.size(65.dp))
                }

            } else {
                item(span = StaggeredGridItemSpan.FullLine) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .wrapContentWidth(),
                        painter = painterResource(id = R.drawable.empty_amico),
                        contentDescription = "Back",
                        tint = Color.Unspecified
                    )
                }


            }


        }
    }
}

