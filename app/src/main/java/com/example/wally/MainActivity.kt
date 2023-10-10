package com.example.wally

import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wally.ui.navigation.SplashScreen
import com.example.wally.ui.navigation.AppNavHost
import com.example.wally.ui.navigation.appScreens
import com.example.wally.ui.navigation.dropletButtons
import com.example.wally.ui.theme.BluishGray
import com.example.wally.ui.theme.WallyTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun MyApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        appScreens.find { it.route == currentDestination?.route } ?: SplashScreen
    var selectedIndex by remember { mutableStateOf(0) }

  
    Scaffold(

        modifier = Modifier.semantics {
            testTagsAsResourceId = true
        },
        bottomBar = {
            if(currentScreen != SplashScreen){  AnimatedNavigationBar(modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 6.dp)
                .height(60.dp),
                selectedIndex = selectedIndex,
                ballColor = White,
                barColor = BluishGray,
                cornerRadius = shapeCornerRadius(25.dp),
                ballAnimation = Parabolic(tween(Duration, easing = LinearOutSlowInEasing)),
                indentAnimation = Height(
                    indentWidth = 56.dp,
                    indentHeight = 15.dp,
                    animationSpec = tween(
                        DoubleDuration,
                        easing = { OvershootInterpolator().getInterpolation(it) })
                )
            ) {
                dropletButtons.forEachIndexed { index, it ->
                    DropletButton(modifier = Modifier.fillMaxSize(),
                        isSelected = selectedIndex == index,
                        onClick = { selectedIndex = index
                        },
                        icon = it.icon,
                        dropletColor = White,
                        animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
                    )
                }

            }}
        },
                content = { innerPadding ->
                    Log.i("Padding", "is = $innerPadding")

                    AppNavHost(
                    navController = navController,
                    modifier = Modifier
                )

        })
}


const val Duration = 500
const val DoubleDuration = 1000