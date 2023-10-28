package com.example.wally.ui.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.wally.ui.OnCategoryClicked
import com.example.wally.ui.model.Category
import com.example.wally.ui.model.categories

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClicked: OnCategoryClicked
) {


    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(  modifier = Modifier
            .padding(9.dp)
            .verticalScroll(rememberScrollState())) {
            categories.forEach{
                Category(it, onCategoryClicked)
            }
            Spacer(modifier = Modifier.size(60.dp))
        }

    }
}


@Composable
fun Category(category: Category, onCategoryClicked: OnCategoryClicked){

    val painter = rememberAsyncImagePainter(category.link)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(6.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onCategoryClicked(category.slug) },
    ) {

        Image(
            painter = painter,
            contentDescription = "category",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )

        Surface(modifier = Modifier.fillMaxSize(), color = if (painter.state is AsyncImagePainter.State.Success) Color.Black.copy(0.3f) else Color(category.color.toColorInt())) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterStart

            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    text = category.title, style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 30.sp
                )
            }
        }
    }
}