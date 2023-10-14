package com.example.wally.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wally.R
import com.example.wally.ui.screens.OnRetryClicked
import com.example.wally.ui.theme.VioletsBlue

@Composable
fun NoInternet(retry: OnRetryClicked, enabled: Boolean) {

    Column(modifier = Modifier.padding(10.dp)) {
        Text(
            text = "Not Internet Connection",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(3.dp),
            color = Color.White.copy(alpha = 0.9f)
        )
        Text(
            text = "It looks like you're not connected to the internet.",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(3.dp),
            color = Color.White.copy(alpha = 0.7f)
        )

        Box {
            Image(
                painter = painterResource(id = R.drawable.no_connection),
                contentDescription = "No connection",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(510.dp)
                    .align(Alignment.Center)
            )
        }

        Button(
            onClick = { retry() }, shape = RoundedCornerShape(20),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            enabled = !enabled,
            colors = ButtonDefaults.buttonColors(containerColor = VioletsBlue)
        ) {
            Text("Try Again...", Modifier.padding(5.dp))
        }

    }

}