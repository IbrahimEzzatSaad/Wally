package com.example.wally.ui.screens.picture

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.wally.data.api.model.PicturesItem

@Composable
fun PictureScreen(picture: PicturesItem) {


        val painter = rememberAsyncImagePainter(picture.urls.full)

        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )

        Image(
            painter = painter,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(transition),
            contentDescription = "custom transition based on painter state"
        )

}