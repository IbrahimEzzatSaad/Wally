package com.example.wally.ui

import com.example.wally.data.api.model.PicturesItem

typealias OnPictureItemClicked = (PicturesItem) -> Unit
typealias OnRetryClicked = () -> Unit
typealias OnFeaturedItemClicked = (List<PicturesItem>, Int) -> Unit
typealias OnFavoriteClicked = (PicturesItem) -> Unit
typealias OnCategoryClicked = (String) -> Unit

@Composable
fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = "Retry")
        }
    }
}