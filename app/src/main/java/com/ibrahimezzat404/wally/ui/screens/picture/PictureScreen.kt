package com.ibrahimezzat404.wally.ui.screens.picture

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.ibrahimezzat404.wally.Duration
import com.ibrahimezzat404.wally.R
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.ui.component.BackButton
import com.ibrahimezzat404.wally.ui.theme.Tulip
import com.ibrahimezzat404.wally.utils.SystemBroadcastReceiver
import com.ibrahimezzat404.wally.utils.toast
import com.exyte.animatednavbar.items.dropletbutton.DropletButton


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PictureScreen(
    modifier: Modifier,
    picture: PictureModel,
    featured: Boolean = false,
    onBackPressed: () -> Unit,
    viewModel: PictureViewModel = hiltViewModel()
) {

    viewModel.onNewPicture(featured)
    val downloadState = viewModel.downloadState.collectAsState().value
    val request = ImageRequest.Builder(LocalContext.current)


    SystemBroadcastReceiver(
        systemAction = DownloadManager.ACTION_DOWNLOAD_COMPLETE,
    ) { intent ->
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)
        viewModel.downloadComplete(id)
    }

    Box {


        if (featured) {
            val featuredItems = viewModel.featured.collectAsState().value
            featuredItems?.let {

                val pagerState =
                    rememberPagerState(initialPage = it.indexOf(it.find { it.id == picture.id }),
                        pageCount = {
                            featuredItems.size
                        })

                HorizontalPager(state = pagerState) { page ->
                    Picture(picture = it[page],
                        isDownloading = downloadState.isDownloading,
                        setWallpaper = {
                            viewModel.setAsWallpaper(
                                request.data(picture.links.downloadLink)
                                    .build()
                            )
                        },
                        downloadPic = { viewModel.startDownload(it) },
                        favoritePic = { viewModel.updateFavorites(it) })
                }
            }

        } else {
            Picture(picture = picture,
                isDownloading = downloadState.isDownloading,
                setWallpaper = {
                    viewModel.setAsWallpaper(
                        request.data(picture.links.downloadLink)
                            .build()
                    )
                },
                downloadPic = { viewModel.startDownload(picture) },
                favoritePic = { viewModel.updateFavorites(picture) })
        }
        BackButton(
            Modifier
                .align(Alignment.TopStart)
                .padding(top = 70.dp, start = 10.dp),
            onBackPressed
        )

    }
}

@Composable
private fun Picture(
    picture: PictureModel,
    isDownloading: Boolean,
    setWallpaper: (PictureModel) -> Unit,
    downloadPic: (PictureModel) -> Unit,
    favoritePic: (PictureModel) -> Unit
) {
    val painter = rememberAsyncImagePainter(picture.urls.regular)
    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f,
        label = ""
    )
    var favorite by rememberSaveable { mutableStateOf(picture.favorite) }
    val context = LocalContext.current
    val openPage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painter,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(transition),
            contentDescription = "custom transition based on painter state"
        )


        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            AnimatedVisibility(
                modifier = Modifier,
                visible = transition == 1f,
                enter = scaleIn(
                    spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    SingleButton(R.drawable.apply) {
                        setWallpaper(picture)
                    }

                    SingleButton(R.drawable.download) {
                        if (!isDownloading) {
                            downloadPic(picture)
                        }
                    }

                    Surface(
                        modifier = Modifier.size(45.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Black.copy(alpha = 0.5F),
                    ) {
                        DropletButton(
                            modifier = Modifier.alpha(0.9f),
                            isSelected = favorite,
                            onClick = {
                                favorite = !favorite
                                favoritePic(picture)
                            },
                            size = 25.dp,
                            iconColor = Color.White,
                            icon = if (favorite) R.drawable.heart else R.drawable.heart_linear,
                            dropletColor = Tulip,
                            animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
                        )
                    }

                }
            }

            Spacer(Modifier.size(20.dp))

            AnimatedVisibility(
                modifier = Modifier,
                visible = transition == 1f,
                enter = fadeIn( animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut()
            ) {
                Text(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .padding(10.dp).clickable {
                        try {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://unsplash.com/@${picture.user.username}?utm_source=wally&utm_medium=referral")
                            )

                            openPage.launch(intent)

                        } catch (_: Exception) {
                            context.toast("Failed to open the profile.")
                        }
                    },
                    text = "Photo by " + picture.user.name.replace("+", " ") + " on Unsplash",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SingleButton(
    icon: Int,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.size(45.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color.Black.copy(alpha = 0.5F),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                modifier = Modifier
                    .size(25.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = icon),
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}
