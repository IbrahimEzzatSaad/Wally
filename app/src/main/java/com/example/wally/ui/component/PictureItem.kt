package com.example.wally.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.screens.OnPictureItemClicked

@Composable
fun PictureItem(
    item: PicturesItem,
    onItemClicked: OnPictureItemClicked,
    modifier: Modifier = Modifier,
    height: Int
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClicked(item) }
            .height(height.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(item.color.toColorInt())
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
            contentDescription = "custom transition based on painter state"
        )

    }
}