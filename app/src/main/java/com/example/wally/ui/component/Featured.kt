package com.example.wally.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.ui.theme.WallyTheme

@Composable
fun Featured(featured: List<PicturesItem>) {

    Column(modifier = Modifier
        .fillMaxWidth()) {


        Text(text = "Featured",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary)

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(8.dp))

        Image(painter = rememberAsyncImagePainter(featured[0].urls.regular ),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillBounds)


        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight() ,horizontalArrangement = Arrangement.SpaceBetween){
            FeaturedImage(rememberAsyncImagePainter(featured[1].urls.regular ))
            FeaturedImage(rememberAsyncImagePainter(featured[2].urls.regular ))

            Box(modifier = Modifier
                .height(80.dp)
                .width(120.dp)
                .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center){

                Image(painter = rememberAsyncImagePainter(featured[3].urls.regular ),
                    contentDescription = "featured image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds)

                Surface(modifier = Modifier.fillMaxSize(), color = Color.Black.copy(0.5f)) {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "+4", style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 24.sp) }
                    }
                }


        }
    }

}
@Composable
fun FeaturedImage(painter : Painter){
    Image(painter = painter,
        contentDescription = "featured image",
        modifier = Modifier
            .height(80.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.FillBounds)
}
