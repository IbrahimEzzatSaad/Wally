package com.example.wally.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wally.R
import com.example.wally.ui.theme.BluishGray

@Composable
fun RefreshButton(onRetry: () -> Unit){
    Box(modifier = Modifier
        .wrapContentWidth()
        .wrapContentHeight()) {


        FilledIconButton(
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .align(Alignment.Center)
                .shadow(5.dp, CircleShape),
            onClick = { onRetry() },
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = BluishGray)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.refresh),
                contentDescription = "refresh"
            )
        }
    }
}