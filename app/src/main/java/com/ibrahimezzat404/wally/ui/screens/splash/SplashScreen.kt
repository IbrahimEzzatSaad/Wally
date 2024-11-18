package com.ibrahimezzat404.wally.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.ibrahimezzat404.wally.R

typealias OnTimeEnd = () -> Unit

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onTimeEnd: OnTimeEnd) {

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


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer(alpha = alpha)
            )
        }
    }

}