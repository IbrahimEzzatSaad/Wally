package com.example.wally.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.wally.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.wally.data.api.model.PicturesItem
import kotlinx.coroutines.delay

typealias OnTimeEnd = () -> Unit

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeEnd : OnTimeEnd) {

    var animationFinished by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (animationFinished) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            }
        ), label = ""
    )

    LaunchedEffect(animationFinished) {
        animationFinished = true
        delay(2000L)
        onTimeEnd()
    }


    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(200.dp).graphicsLayer(alpha = alpha)
                )
            }
        }
    }
}