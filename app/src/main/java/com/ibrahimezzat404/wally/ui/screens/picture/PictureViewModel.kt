package com.ibrahimezzat404.wally.ui.screens.picture

import android.app.DownloadManager
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.ibrahimezzat404.wally.data.api.model.PictureModel
import com.ibrahimezzat404.wally.domain.usecases.GetFeatured
import com.ibrahimezzat404.wally.domain.usecases.UpdateFavorite
import com.ibrahimezzat404.wally.ui.model.DownloadState
import com.ibrahimezzat404.wally.utils.getImageUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val updateFavorite: UpdateFavorite,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val downloadManager: DownloadManager,
    private val getFeatured: GetFeatured,
    private val wallpaperManager: WallpaperManager
) : ViewModel() {


    private val _downloadState = MutableStateFlow(DownloadState(null, false))
    val downloadState: StateFlow<DownloadState> = _downloadState

    private val _featured = MutableStateFlow<List<PictureModel>?>(null)
    val featured: StateFlow<List<PictureModel>?> =
        _featured.asStateFlow()

    fun onNewPicture(featured: Boolean) {
        viewModelScope.launch(mainDispatcher) {
            if(featured){
                subscribeToFeaturedUpdates()
            }
        }
    }

    private fun subscribeToFeaturedUpdates() {
        viewModelScope.launch(mainDispatcher) {
            getFeatured().distinctUntilChanged().collect {
                onNewFeaturedList(it)
            }
        }
    }

    private suspend fun onNewFeaturedList(featured: List<PictureModel>) {
            _featured.emit(featured)
    }

    fun startDownload(picture: PictureModel) {
            val req = DownloadManager.Request(
                Uri.parse(picture.links.downloadLink)
            ).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    "UnSplash-${picture.id}.jpg"
                )

            val downloadId = downloadManager.enqueue(req)
            viewModelScope.launch {
                _downloadState.emit(DownloadState(downloadId, true))
            }

    }

    fun downloadComplete(id: Long?) {
        viewModelScope.launch {
            if (id == downloadState.value.mDownloadId && id != null) {
                _downloadState.emit(DownloadState(null, false))
            }
        }
    }

    fun setAsWallpaper(request: ImageRequest) {
        viewModelScope.launch(Dispatchers.IO) {
                try {
                    val context = request.context
                    val result = request.context.imageLoader.execute(request)

                    // Check if the request was successful and extract the bitmap
                    if (result is SuccessResult) {
                        val bitmap: Bitmap = result.drawable.toBitmap()
                        val imageBitmap = bitmap.asImageBitmap().asAndroidBitmap()
                        context.startActivity(wallpaperManager.getCropAndSetWallpaperIntent(context.getImageUri(imageBitmap)))
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
        }
    }

    fun updateFavorites(picture: PictureModel) {
        viewModelScope.launch(mainDispatcher) {
            updateFavorite(picture)
        }
    }


}