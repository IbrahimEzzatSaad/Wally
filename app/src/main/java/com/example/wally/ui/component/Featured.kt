package com.example.wally.ui.component

import android.graphics.ImageDecoder
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.OnFeaturedItemClicked

@Composable
fun Featured(featured: List<PicturesItem>, onFeaturedClicked: OnFeaturedItemClicked) {

    Column(
        modifier = Modifier.padding(3.dp)
            .fillMaxWidth()
    ) {


        Text(
            text = "Featured",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onFeaturedClicked(featured, 0) }
                .height(200.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(featured[0].color.toColorInt())
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(featured[0].urls.regular)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = "image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }


        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (featured.size > 4) {
                FeaturedImage(
                    rememberAsyncImagePainter(featured[1].urls.regular),
                    modifier = Modifier.clickable { onFeaturedClicked(featured, 1) },
                    color = featured[1].color
                )
                FeaturedImage(
                    rememberAsyncImagePainter(featured[2].urls.regular),
                    modifier = Modifier.clickable { onFeaturedClicked(featured, 2) },
                    color = featured[2].color
                )

                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(featured[3].urls.regular),
                        contentDescription = "featured image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )

                    Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(0.5f)) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "+4", style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.size(15.dp))
    }
}

@Composable
fun FeaturedImage(painter: Painter, color: String, modifier: Modifier) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .height(80.dp)
            .width(120.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(color.toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Image(
            painter = painter,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "featured image",
            contentScale = ContentScale.FillBounds
        )
    }
}

