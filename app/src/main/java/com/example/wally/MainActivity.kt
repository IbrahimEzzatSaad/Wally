package com.example.wally

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.wally.ui.component.BackButton
import com.example.wally.ui.component.ConnectivityStatus
import com.example.wally.ui.navigation.SplashScreen
import com.example.wally.ui.navigation.AppNavHost
import com.example.wally.ui.navigation.PictureScreen
import com.example.wally.ui.navigation.appScreens
import com.example.wally.ui.navigation.dropletButtons
import com.example.wally.ui.theme.BluishGray
import com.example.wally.ui.theme.WallyTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WallyTheme(
                dynamicColor = false
            ) {
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

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class, ExperimentalCoroutinesApi::class
)
@Composable
@Preview
private fun MyApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        appScreens.find { it.route == currentDestination?.route } ?: SplashScreen
    val indexCurrentScreen =
        dropletButtons.indexOfFirst { it.destination.route == currentScreen.route }
    var selectedIndex = indexCurrentScreen



    Scaffold(
        modifier = Modifier
            .semantics {
                testTagsAsResourceId = true
            },
        topBar = {

        },
        bottomBar = {
            if (indexCurrentScreen != -1) {
                AnimatedNavigationBar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .height(60.dp),
                    selectedIndex = selectedIndex,
                    ballColor = MaterialTheme.colorScheme.onPrimary,
                    barColor = BluishGray,
                    cornerRadius = shapeCornerRadius(25.dp),
                    ballAnimation = Teleport(tween(Duration, easing = LinearOutSlowInEasing)),
                    indentAnimation = Height(
                        indentWidth = 56.dp,
                        indentHeight = 15.dp,
                        animationSpec = tween(
                            DoubleDuration,
                            easing = { OvershootInterpolator().getInterpolation(it) })
                    )
                ) {
                    dropletButtons.forEachIndexed { index, it ->
                        DropletButton(
                            modifier = Modifier.fillMaxSize(),
                            isSelected = selectedIndex == index,
                            onClick = {
                                if (currentScreen != it.destination) {
                                    navController.popBackStack(it.destination.route, true)
                                    navController.navigate(route = it.destination.route) {
                                        launchSingleTop = true
                                    }
                                }
                                selectedIndex = index
                            },
                            icon = it.icon,
                            dropletColor = White,
                            animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
                        )
                    }

                }
            }
        }, content = { innerPadding ->
            Column(Modifier.padding(top = if (currentScreen != PictureScreen ) innerPadding.calculateTopPadding() else 0.dp)) {
                ConnectivityStatus( 0F)

                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(
                        start = 15.dp,
                        end = 15.dp
                    ),
                )
            }
        })
}


const val Duration = 500
const val DoubleDuration = 1000