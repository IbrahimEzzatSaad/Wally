package com.example.wally

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import com.example.wally.ui.navigation.SplashScreen
import com.example.wally.ui.navigation.AppNavHost
import com.example.wally.ui.navigation.CategoriesScreen
import com.example.wally.ui.navigation.FavoriteScreen
import com.example.wally.ui.navigation.HomeScreen
import com.example.wally.ui.navigation.SearchScreen
import com.example.wally.ui.navigation.appScreens
import com.example.wally.ui.navigation.dropletButtons
import com.example.wally.ui.theme.BluishGray
import com.example.wally.ui.theme.DarkJungle
import com.example.wally.ui.theme.WallyTheme
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Teleport
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.items.dropletbutton.DropletButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

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

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .semantics {
                testTagsAsResourceId = true
            },
        topBar = {
            if (currentScreen != SplashScreen) {
                CenterAlignedTopAppBar(

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
                        if (indexCurrentScreen == -1) {
                            IconButton(modifier = Modifier.wrapContentWidth(),
                                onClick = { navController.popBackStack() }) {

                                Icon(
                                    modifier = Modifier.fillMaxHeight(),
                                    painter = painterResource(id = R.drawable.back_button),
                                    contentDescription = "Back"
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = DarkJungle
                    ),
                    scrollBehavior = scrollBehavior
                )
            }
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
                                when (it.destination) {
                                    FavoriteScreen -> {
                                        if (currentScreen != FavoriteScreen) {
                                            navController.navigate(route = FavoriteScreen.route)
                                        }
                                    }

                                    HomeScreen -> {
                                        if (currentScreen != HomeScreen) {
                                            navController.navigate(route = HomeScreen.route)
                                        }
                                    }

                                    SearchScreen -> {
                                        if (currentScreen != SearchScreen) {
                                            navController.navigate(route = SearchScreen.route)
                                        }
                                    }

                                    CategoriesScreen -> {
                                        if (currentScreen != CategoriesScreen) {
                                            navController.navigate(route = CategoriesScreen.route)
                                        }
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

            AppNavHost(
                navController = navController,
                modifier = if (currentScreen != SplashScreen) Modifier.padding(
                    top = innerPadding.calculateTopPadding()
                ) else Modifier
            )
        })

}


const val Duration = 500
const val DoubleDuration = 1000