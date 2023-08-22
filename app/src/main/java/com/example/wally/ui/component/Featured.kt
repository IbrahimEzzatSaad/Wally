package com.example.wally.ui.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.wally.R
import com.example.wally.data.api.model.PicturesItem
import com.example.wally.data.api.model.Urls

@Composable
fun Featured(featured: List<PicturesItem>) {

    Column {
        Image(painter = rememberAsyncImagePainter(featured[0].urls.regular ),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f))


        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(5.dp))



        Row (modifier = Modifier.fillMaxWidth() ,horizontalArrangement = Arrangement.SpaceEvenly){
            FeaturedImage(rememberAsyncImagePainter(featured[1].urls.regular ))
            FeaturedImage(rememberAsyncImagePainter(featured[2].urls.regular ))
            FeaturedImage(rememberAsyncImagePainter(featured[3].urls.regular ))

        }
    }

}
@Composable
fun FeaturedImage(painter : Painter){
    Image(painter = painter,
        contentDescription = "featured image",
        modifier = Modifier.height(100.dp).width(100.dp))
}

