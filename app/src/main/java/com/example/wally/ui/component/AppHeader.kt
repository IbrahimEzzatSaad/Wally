package com.example.wally.ui.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wally.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(navigationIcon: Boolean, onBack: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(15.dp),
        title = {
            Icon(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Back",
                tint = Color.Unspecified
            )

        },
        navigationIcon = {
            if (navigationIcon) {
                BackButton {
                    onBack()
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor =
            MaterialTheme.colorScheme.surface
        ),
    )
}