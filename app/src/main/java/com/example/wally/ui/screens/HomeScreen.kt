package com.example.wally.ui.screens

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wally.data.api.model.PicturesItem
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale


typealias OnPictureItemClicked = (PicturesItem) -> Unit
const val ARTICLE_LIST_TEST_TAG = "articles_list"


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(onArticleItemClicked: OnPictureItemClicked,
               modifier: Modifier = Modifier,
               viewModel: PictureViewModel = hiltViewModel()) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val listState = rememberLazyStaggeredGridState()
        val pictures by viewModel.pictures.collectAsState()

        Column {
            Text(text = "All", modifier = Modifier.padding(5.dp))

            Spacer(modifier = Modifier.size(5.dp))

            Box(modifier = Modifier) {
                pictures?.let {

                    PicturesList(
                        it,
                        modifier = modifier.testTag(ARTICLE_LIST_TEST_TAG),
                        onArticleItemClicked = onArticleItemClicked,
                        listState = listState
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .align(Alignment.Center)
                            .height(48.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.testTag("myProgressIndicator"))
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PicturesList(
    picturesList: List<PicturesItem>,
    onArticleItemClicked: OnPictureItemClicked,
    modifier: Modifier = Modifier,
    listState: LazyStaggeredGridState = rememberLazyStaggeredGridState()
) {
    LazyVerticalStaggeredGrid(
        state = listState,
        columns = if (LocalConfiguration.current.orientation == ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2),
        modifier = modifier) {

           items(picturesList) { picture ->

               PictureItem(
                   modifier = Modifier
                       .fillMaxSize(),
                   item = picture,
                   onItemClicked = onArticleItemClicked
               )
           }
    }
}

@Composable
private fun PictureItem(
    item: PicturesItem,
    onItemClicked: OnPictureItemClicked,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clickable { onItemClicked(item) }
            .padding(5.dp)
    ) {
        val painter = rememberAsyncImagePainter(item.urls.regular )
        val state = painter.state

        val transition by animateFloatAsState(
            targetValue = if (state is AsyncImagePainter.State.Success) 1f else 0f, label = "")



        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height((item.height / 12).dp)
                .clip(RoundedCornerShape(10.dp)),
            contentDescription = "custom transition based on painter state",


        )


    }


}

@Composable
fun LoadingAnimation() {
    val animation = rememberInfiniteTransition()
    val progress by animation.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Restart,
        ), label = ""
    )

    Box(
        modifier = Modifier
            .size(60.dp)
            .scale(progress)
            .alpha(1f - progress)
            .border(
                5.dp,
                color = androidx.compose.ui.graphics.Color.Black,
                shape = CircleShape
            )
    )
}