package com.ibrahimezzat404.wally.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ibrahimezzat404.wally.Duration
import com.ibrahimezzat404.wally.R
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.utils.OnFavoriteClicked
import com.ibrahimezzat404.wally.utils.OnPictureItemClicked
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.ibrahimezzat404.wally.ui.theme.Tulip

@Composable
fun PictureItem(
    modifier: Modifier = Modifier,
    item: PictureModel,
    onItemClicked: OnPictureItemClicked,
    onFavoriteClicked: OnFavoriteClicked,
    isFavorite: Boolean,
    height: Int) {


    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(item.urls.regular)
        .memoryCacheKey(item.urls.regular)
        .diskCacheKey(item.urls.regular)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()


    Card(
        modifier = modifier
            .clickable { onItemClicked(item) }
            .height(height.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(item.color.toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Box {

            AsyncImage(
                model = imageRequest,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
                contentDescription = ""
            )

            DropletButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .alpha(if (isFavorite) 1f else 0.6f),
                isSelected = isFavorite,
                onClick = {
                    onFavoriteClicked(item)
                },

                icon = R.drawable.heart,
                dropletColor = Tulip,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )

        }
    }
}