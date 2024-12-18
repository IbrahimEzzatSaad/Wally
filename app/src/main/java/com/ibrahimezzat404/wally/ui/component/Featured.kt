package com.ibrahimezzat404.wally.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.utils.OnFeaturedItemClicked

@Composable
fun Featured(featured: List<PictureModel>, onFeaturedClicked: OnFeaturedItemClicked) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {


        Text(
            text = "Featured",
            modifier = Modifier.padding(top = 10.dp),
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
                .clickable { onFeaturedClicked(featured[0]) }
                .height(200.dp)
                .fillMaxWidth()
                .padding(3.dp)
            ,
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(featured[0].color.toColorInt())
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
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
                .height(5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (featured.size > 4) {
                FeaturedImage(
                    link = featured[1].urls.regular,
                    modifier = Modifier.weight(1f).fillMaxWidth().aspectRatio(1.2f).clickable { onFeaturedClicked(featured[1]) },
                    color = featured[1].color
                )
                FeaturedImage(
                    link = featured[2].urls.regular,
                    modifier = Modifier.weight(1f).fillMaxWidth().aspectRatio(1.2f).clickable { onFeaturedClicked(featured[2]) }.padding(start = 5.dp , end= 5.dp),
                    color = featured[2].color
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .aspectRatio(1.2f)
                        .padding(2.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onFeaturedClicked(featured[4]) },
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
                                text = "+7", style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
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
fun FeaturedImage(modifier: Modifier, link: String, color: String) {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(link)
        .memoryCacheKey(link)
        .diskCacheKey(link)
        .diskCachePolicy(CachePolicy.ENABLED)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()

    Card(
        modifier = modifier
            .padding(2.dp)
        ,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(color.toColorInt())
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        AsyncImage(
            model = imageRequest,
            modifier = Modifier.fillMaxSize(),
            contentDescription = "featured image",
            contentScale = ContentScale.FillBounds
        )
    }
}

