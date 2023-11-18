package com.example.wally.ui.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.wally.R
import com.example.wally.utils.OnPictureItemClicked
import com.example.wally.ui.component.PictureItem
import com.example.wally.ui.component.RefreshButton
import com.example.wally.ui.component.SearchField
import com.example.wally.ui.component.StaggeredGrid


@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onPictureItemClicked: OnPictureItemClicked,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchResult = viewModel.search.collectAsLazyPagingItems()
    val searchQuery = viewModel.searchQuery.collectAsState().value
    val interactionSource = remember { MutableInteractionSource() }
    var hideKeyboard by remember { mutableStateOf(false) }

    StaggeredGrid(modifier = modifier.clickable(
        interactionSource = interactionSource,
        indication = null
    ) { hideKeyboard = true }) {

        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                text = "Search",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 50.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }


        item(span = StaggeredGridItemSpan.FullLine) {
            Text(
                text = "searching through hundreds of photos will be so much easier now.",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
            )
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            SearchField(
                searchQuery = searchQuery,
                onValueChange = { viewModel.search(it) },
                hideKeyboard = hideKeyboard,
                onFocusClear = { hideKeyboard = false }
            )
        }

        if (searchResult.itemSnapshotList.isNotEmpty()) {

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
                    contentDescription = "Empty",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

