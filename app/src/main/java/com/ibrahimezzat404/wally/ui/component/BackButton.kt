package com.ibrahimezzat404.wally.ui.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.ibrahimezzat404.wally.R

@Composable
fun BackButton(modifier: Modifier = Modifier, backPressed : () -> Unit) {
    IconButton(modifier = modifier.wrapContentWidth(),
        onClick = { backPressed() } ) {

        Icon(
            modifier = Modifier.fillMaxHeight(),
            painter = painterResource(id = R.drawable.back_button),
            contentDescription = "Back"
        )
    }
}