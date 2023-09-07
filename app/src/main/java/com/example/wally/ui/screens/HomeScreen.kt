package com.example.wally.ui.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImagePainter
import coil.compose.LocalImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.ErrorMessage
import com.example.wally.ui.LoadingNextPageItem
import com.example.wally.ui.PageLoader
import com.example.wally.ui.component.Featured
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


typealias OnPictureItemClicked = (PicturesItem) -> Unit

const val PICTURE_LIST_TEST_TAG = "pictures_list"


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onPictureItemClicked: OnPictureItemClicked,
    modifier: Modifier = Modifier,
    viewModel: PictureViewModel = hiltViewModel()
) {


    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        val pictures: LazyPagingItems<PicturesItem> = viewModel.pictures.collectAsLazyPagingItems()
        val listState = rememberLazyStaggeredGridState()
        val featured by viewModel.featured.collectAsState()

        LazyVerticalStaggeredGrid(
            state = listState,
            columns = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
                StaggeredGridCells.Adaptive(minSize = 175.dp)
            } else StaggeredGridCells.Fixed(2)
        ) {

            item(span = StaggeredGridItemSpan.FullLine, content = {
                featured?.let { Featured(it) }
            })

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(modifier = Modifier.size(15.dp))
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


            items(pictures.itemCount,
                key = pictures.itemKey { it.id },
                contentType = pictures.itemContentType{ it }) { index ->
                pictures[index]?.let{
                    PictureItem(
                        modifier = Modifier
                            .fillMaxSize(),
                        item = it,
                        onItemClicked = onPictureItemClicked,
                        height = (it.height / 12)
                    )
                }
            }
        }
    }

}


@Composable
private fun PictureItem(
    item: PicturesItem,
    onItemClicked: OnPictureItemClicked,
    modifier: Modifier = Modifier,
    height : Int
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClicked(item) }
            .height(height.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {

        val painter = rememberAsyncImagePainter(item.urls.small)
        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )

        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(transition),
            contentDescription = "custom transition based on painter state")

    }
}