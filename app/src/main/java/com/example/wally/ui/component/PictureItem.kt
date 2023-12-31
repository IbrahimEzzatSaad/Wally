package com.example.wally.ui.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.wally.Duration
import com.example.wally.R
import com.example.wally.data.api.model.PictureModel
import com.example.wally.utils.OnFavoriteClicked
import com.example.wally.utils.OnPictureItemClicked
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import com.example.wally.ui.theme.Tulip

@Composable
fun PictureItem(
    item: PictureModel,
    onItemClicked: OnPictureItemClicked,
    onFavoriteClicked: OnFavoriteClicked,
    modifier: Modifier = Modifier,
    height: Int) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onItemClicked(item) }
            .height(height.dp)
            .padding(5.dp)
        ,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(item.color.toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        var favorite by rememberSaveable { mutableStateOf(item.favorite) }

        val painter = rememberAsyncImagePainter(item.urls.regular)

        val transition by animateFloatAsState(
            targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
            label = ""
        )

        Box {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(transition),
                contentDescription = "custom transition based on painter state"
            )

            DropletButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(6.dp)
                    .height(30.dp)
                    .width(30.dp)
                    .alpha(if (favorite) 1f else 0.6f),
                isSelected = favorite,
                onClick = {
                    favorite = !favorite
                    onFavoriteClicked(item)
                },

                icon = R.drawable.heart,
                dropletColor = Tulip,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )

        }
    }
}