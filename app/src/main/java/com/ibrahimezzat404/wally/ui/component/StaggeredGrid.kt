package com.ibrahimezzat404.wally.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.ibrahimezzat404.wally.ui.screens.home.PICTURE_LIST_TEST_TAG

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    content: LazyStaggeredGridScope.() -> Unit
) {
    val listState: LazyStaggeredGridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        modifier = modifier
            .testTag(PICTURE_LIST_TEST_TAG),
        state = listState,
        columns = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            StaggeredGridCells.Adaptive(minSize = 175.dp)
        } else StaggeredGridCells.Fixed(2),
        content = content
    )
}